package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.WordWithHoleData;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyTextToSpeech;
import fr.dut.ptut2021.utils.MyVibrator;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private CreateDatabase db;
    private TextView word;
    private ImageView image, help;
    private Button answer1, answer2, answer3;
    private Map<String, Word> mapChooseData;
    private List<String> listAnswer;
    private String goodAnswer;
    private String[] alphabetTab, syllableTab;
    private final int MAX_GAME_PLAYED = 4;
    private int userId, gameId, gamePlayed = 1, nbTry = 0, nbWrongAnswer = 0, difficulty = 1;
    private boolean delay = false, haveWin = false;
    private Random random = new Random();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        GlobalUtils.verifyIfSoundIsOn(this);
        db = CreateDatabase.getInstance(WordWithHole.this);
        getSharedPref();

        fillDatabase();
        initLayoutContent();
        initGame();

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        help.setOnClickListener(this);
    }

    private void getSharedPref() {
        userId = MySharedPreferences.getUserId(this);
        gameId = MySharedPreferences.getGameId(this);
    }

    private void fillDatabase() {
        List<String[]> dataTab = new ArrayList<>();
        dataTab.add(getResources().getStringArray(R.array.WWHdifficulty1));
        dataTab.add(getResources().getStringArray(R.array.WWHdifficulty2));
        dataTab.add(getResources().getStringArray(R.array.WWHdifficulty3));
        dataTab.add(getResources().getStringArray(R.array.WWHdifficulty4));

        for (int i = 0; i < dataTab.size(); i++) {
            for (String str : dataTab.get(i)) {
                db.gameDao().insertWWHData(new WordWithHoleData(userId, str, i+1));
            }
        }

        alphabetTab = getResources().getStringArray(R.array.alphabet);
        syllableTab = getResources().getStringArray(R.array.syllable);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initGame() {
        initMapChooseWord();
        initListAnswer();
        setLayoutContent();
        readInstruction(false);
        readWord();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initMapChooseWord() {
        List<WordWithHoleData> listData = new ArrayList<>(db.gameDao().getAllWWHData(userId));
        mapChooseData = new HashMap<>();
        int maxDifficulty = db.gameDao().getWWHDataMaxDifficulty(userId);
        List<List<List<String>>> listAllData = new ArrayList<>();

        for (int i = 0; i < maxDifficulty; i++)
            listAllData.add(new ArrayList<>());

        for (int dif = 1; dif <= maxDifficulty; dif++) {
            List<String> listDataNeverUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, -1);
            List<String> listDataNotUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, 0);
            List<String> listDataUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, 1);

            listAllData.get(dif - 1).add(listDataNeverUsed);
            listAllData.get(dif - 1).add(listDataNotUsed);
            listAllData.get(dif - 1).add(listDataUsed);

            Collections.shuffle(listAllData.get(dif - 1).get(1));
            Collections.shuffle(listAllData.get(dif - 1).get(2));

            Log.e("APPLOG", "List Dif " + dif + " (-1): " + listAllData.get(dif - 1).get(0));
            Log.e("APPLOG", "List Dif " + dif + " (0): " + listAllData.get(dif - 1).get(1));
            Log.e("APPLOG", "List Dif " + dif + " (1): " + listAllData.get(dif - 1).get(2));
        }
        for (int i = 0; i < listAllData.size(); i++) {
            if (mapChooseData.size() <= MAX_GAME_PLAYED) {
                fillMapChooseWord(listAllData, i + 1);
                difficulty = i + 1;
            }
        }
        db.gameDao().updateAllWWHDataLastUsed(userId);
    }

    private void fillMapChooseWord(List<List<List<String>>> listData, int difficulty) {
        List<Word> words;

        for (int j = 0; j < listData.get(difficulty-1).size(); j++) {
            for (int k = 0; k < listData.get(difficulty-1).get(j).size(); k++) {

                if (mapChooseData.size() <= MAX_GAME_PLAYED && listData.get(difficulty-1).get(j).size() > 0) {
                    String answer = listData.get(difficulty-1).get(j).get(k);
                    if (!mapChooseData.containsKey(answer) &&
                            (difficulty == listData.size() || db.gameDao().getWWHDataByResult(userId, answer).getWinStreak() < 2)
                    ) {
                        Log.e("APPLOG", "answer : " + answer);
                        words = db.appDao().getWordIfContain('%' + answer + '%');

                        if (words.size() > 0) {
                            for (Map.Entry<String, Word> val : mapChooseData.entrySet())
                                words.remove(mapChooseData.get(val.getKey()));

                            for (int i = 0; i < 3; i++)
                                Collections.shuffle(words);

                            for (int i = difficulty; i < listData.size(); i++) {
                                for (int l = 0; l < listData.get(i).size(); l++) {
                                    for (int m = 0; m < listData.get(i).get(l).size(); m++) {
                                        String syllable = listData.get(i).get(l).get(m);

                                        if (syllable.length() == 2 && !answer.equals(syllable)) {
                                            if (answer.contains(String.valueOf(syllable.charAt(0))) ||
                                                    answer.contains(String.valueOf(syllable.charAt(1)))) {
                                                for (int n = 0; n < words.size(); n++) {
                                                    if (words.get(n).getWord().contains(syllable)) {
                                                        words.remove(n);
                                                        n--;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (words.size() > 0) {
                                for (int n = 0; n < words.size(); n++)
                                    Log.e("APPLOG", "word : " + words.get(n).getWord());
                                int rand = random.nextInt(words.size());
                                mapChooseData.put(answer, words.get(rand));
                                Log.e("APPLOG", "FINAL : " + answer + " - " + words.get(rand).getWord());
                                words.clear();
                            }
                            Log.e("APPLOG", "------------------------------");
                        }
                    }
                }
            }
        }
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();
        List<String> listString = new ArrayList<>(mapChooseData.keySet());
        listAnswer.add(listString.get(gamePlayed - 1));
        goodAnswer = listAnswer.get(0);

        while (listAnswer.size() < 3) {
            if (goodAnswer.length() == 1) {
                int rand = random.nextInt(alphabetTab.length);
                if (!listAnswer.contains(alphabetTab[rand])) {
                    listAnswer.add(alphabetTab[rand]);
                }
            } else {
                int rand = random.nextInt(syllableTab.length);
                if (!listAnswer.contains(syllableTab[rand])) {
                    listAnswer.add(syllableTab[rand]);
                }
            }
        }
        Collections.shuffle(listAnswer);
    }

    private void initLayoutContent() {
        word = findViewById(R.id.word_wordWithHole);
        image = findViewById(R.id.imageWord_wordWithHole);
        answer1 = findViewById(R.id.buttonAnswer1_wordWithHole);
        answer2 = findViewById(R.id.buttonAnswer2_wordWithHole);
        answer3 = findViewById(R.id.buttonAnswer3_wordWithHole);
        help = findViewById(R.id.ic_help_wordWithHole);
    }

    private void setLayoutContent() {
        word.setText(holeTheWord());
        image.setImageResource(mapChooseData.get(goodAnswer).getImage());
        answer1.setText(listAnswer.get(0));
        answer2.setText(listAnswer.get(1));
        answer3.setText(listAnswer.get(2));
    }

    private void readInstruction(boolean help) {
        int sum = 0;
        int limit = 2;
        if (!help) {
            for (int star : db.gameLogDao().getAllGameLogStarsLimit(userId, gameId, limit))
                sum += star;
        }
        if (help || sum <= limit) {
            delay = true;
            MyTextToSpeech.speachText(this, "Trouve la lettre ou la syllabe manquante.");
            new Handler().postDelayed(() -> {
                delay = false;
            }, 2000);
        }
    }

    private void readWord() {
        MyTextToSpeech.speachText(this, mapChooseData.get(goodAnswer).getWord());
    }

    private void textAnimation(boolean goodAnswer) {
        if (goodAnswer) {
            YoYo.with(Techniques.Pulse)
                    .duration(1000)
                    .playOn(findViewById(R.id.word_wordWithHole));
        } else {
            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .playOn(findViewById(R.id.word_wordWithHole));
        }
    }

    private void playSound(boolean isGoodAnswer) {
        if (isGoodAnswer)
            MyMediaPlayer.playSound(this, R.raw.correct_answer);
        else
            MyMediaPlayer.playSound(this, R.raw.wrong_answer);
    }

    private String concatWrongAnswer(int indAnswer) {
        String s = mapChooseData.get(goodAnswer).getWord();
        s = s.replaceFirst(goodAnswer, listAnswer.get(indAnswer - 1));
        return s;
    }

    private String holeTheWord() {
        String s = mapChooseData.get(goodAnswer).getWord();
        String rep = "_";
        if (goodAnswer.length() == 2)
            rep = "__";
        s = s.replaceFirst(goodAnswer, rep);
        return s;
    }

    private void setWordAndAddDelay(int indWrongAnswer) {
        delay = true;
        word.setText(concatWrongAnswer(indWrongAnswer));
        word.setTextColor(Color.RED);
        playSound(false);
        textAnimation(false);

        nbTry++;
        if (nbTry < 2) {
            new Handler().postDelayed(() -> {
                delay = false;
                word.setText(holeTheWord());
                word.setTextColor(Color.BLACK);
                readWord();
            }, 2000);
        }else {
            delay = true;
            new Handler().postDelayed(() -> {
                colorIfGoodAnswer(answer1);
                colorIfGoodAnswer(answer2);
                colorIfGoodAnswer(answer3);
                replay();
            }, 2000);
        }
    }

    private void colorIfGoodAnswer(Button button) {
        if (button.getText() == goodAnswer)
            button.setBackgroundColor(Color.GREEN);
    }

    private int starsNumber() {
        if (nbWrongAnswer < 2)
            return 3;
        else if (nbWrongAnswer < 5)
            return 2;
        else
            return 1;
    }

    private void replay() {
        word.setText(mapChooseData.get(goodAnswer).getWord());
        word.setTextColor(Color.GREEN);

        playSound(true);
        textAnimation(true);

        updateGameData();

        gamePlayed++;
        delay = true;

        new Handler().postDelayed(() -> {
            delay = false;

            if (gamePlayed <= MAX_GAME_PLAYED) {
                nbTry = 0;
                initListAnswer();
                setLayoutContent();
                word.setTextColor(Color.BLACK);
                readWord();
                resetButton();
            } else {
                int stars = starsNumber();
                addGameLogInDb(stars);
                GlobalUtils.startResultPage(WordWithHole.this, stars);
            }
        }, 3000);
    }

    private void resetButton() {
        final String BASIC_BUTTON_COLOR = "#00BCD4";
        answer1.setBackgroundColor(Color.parseColor(BASIC_BUTTON_COLOR));
        answer1.setEnabled(true);
        answer2.setBackgroundColor(Color.parseColor(BASIC_BUTTON_COLOR));
        answer2.setEnabled(true);
        answer3.setBackgroundColor(Color.parseColor(BASIC_BUTTON_COLOR));
        answer3.setEnabled(true);
    }

    private void updateGameData() {
        WordWithHoleData data = db.gameDao().getWWHDataByResult(userId, goodAnswer);
        data.setLastUsed(1);

        if (nbTry == 0) {
            data.setWin(data.getWin() + 1);
            data.setWinStreak(data.getWinStreak() + 1);
            data.setLoseStreak(0);
        } else {
            data.setLose(data.getLose() + 1);
            data.setLoseStreak(data.getLoseStreak() + 1);
            data.setWinStreak(0);
        }
        db.gameDao().updateWWHData(data);
    }

    private void addGameLogInDb(int stars) {
        GameLog gameLog = new GameLog(gameId, -1, userId, stars, difficulty);
        db.gameLogDao().insertGameLog(gameLog);
    }

    private void verifyAnswer(Button answer, int numAnswer) {
        MyVibrator.vibrate(WordWithHole.this, 35);
        if (answer.getText() == goodAnswer) {
            if (gamePlayed+1 == MAX_GAME_PLAYED)
                haveWin = true;
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            setWordAndAddDelay(numAnswer);
            answer.setEnabled(false);
            nbWrongAnswer++;
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            switch (v.getId()) {
                case R.id.ic_help_wordWithHole:
                    readInstruction(true);
                    break;

                case R.id.buttonAnswer1_wordWithHole:
                    verifyAnswer(answer1, 1);
                    break;

                case R.id.buttonAnswer2_wordWithHole:
                    verifyAnswer(answer2, 2);
                    break;

                case R.id.buttonAnswer3_wordWithHole:
                    verifyAnswer(answer3, 3);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyTextToSpeech.stop(WordWithHole.this);
    }

    @Override
    public void onBackPressed() {
        if(!haveWin)
            super.onBackPressed();
    }
}