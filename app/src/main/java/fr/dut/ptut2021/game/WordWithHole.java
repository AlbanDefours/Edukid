package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.WordWithHoleData;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.models.database.log.GameResultLog;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyTextToSpeech;
import fr.dut.ptut2021.utils.MyVibrator;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private CreateDatabase db;
    private TextView word;
    private ImageView image, help;
    private Button answer1, answer2, answer3;
    private List<WordWithHoleData> listData;
    private Map<String, Word> mapChooseData;
    private List<String> listAnswer;
    private String goodAnswer;
    private String[] alphabetTab, syllableTab;
    private final int MAX_GAME_PLAYED = 4;
    private int userId, gameId, gamePlayed = 1, nbTry = 0, nbWin = 0;
    private boolean delay = false;
    private Random random = new Random();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);
        getSharedPref();

        fillDatabase();
        initGame();
        initListAnswer();
        initLayoutContent();
        setLayoutContent();
        readInstructionAndWord(db.gameLogDao().tabGameResultLogIsEmpty(userId, gameId));

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
        alphabetTab = getResources().getStringArray(R.array.alphabet);
        for (String letter : alphabetTab) {
            db.gameDao().insertWWHData(new WordWithHoleData(db.gameDao().getWWHMaxId()+1, userId, letter, 1));
        }

        syllableTab = getResources().getStringArray(R.array.syllable);
        for (String syllable : syllableTab) {
            db.gameDao().insertWWHData(new WordWithHoleData(db.gameDao().getWWHMaxId()+1, userId, syllable, 2));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initGame() {
        listData = new ArrayList<>(db.gameDao().getAllWWHData(userId));
        mapChooseData = new HashMap<>();

        List<List<String>> listDataDif1 = new ArrayList<>();
        List<List<String>> listDataDif2 = new ArrayList<>();

        for (int dif = 1; dif <= 2; dif++) {
            List<String> listDataNeverUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, -1);
            List<String> listDataNotUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, 0);
            List<String> listDataUsed = db.gameDao().getAllWWHDataLastUsed(listData, dif, 1);

            if (dif == 1) {
                listDataDif1.add(listDataNeverUsed);
                listDataDif1.add(listDataNotUsed);
                listDataDif1.add(listDataUsed);
            } else {
                listDataDif2.add(listDataNeverUsed);
                listDataDif2.add(listDataNotUsed);
                listDataDif2.add(listDataUsed);
            }
        }

        Log.e("APP_LOG", "DIFFICULTY 1 - Never Used" + listDataDif1.get(0));
        Log.e("APP_LOG", "DIFFICULTY 1 - Not Used" + listDataDif1.get(1));
        Log.e("APP_LOG", "DIFFICULTY 1 - Used" + listDataDif1.get(2));

        fillMapChooseWord(listDataDif1, false);
        if (mapChooseData.size() <= MAX_GAME_PLAYED) {
            fillMapChooseWord(listDataDif2, true);
        }

        db.gameDao().updateAllWWHDataLastUsed(userId);
    }

    private void fillMapChooseWord(List<List<String>> list, boolean lastDifficulty) {
        List<Word> words = new ArrayList<>();

        for (int j = 0; j < list.size(); j++) {
            for (int k = 0; k < list.get(j).size(); k++) {

                if (mapChooseData.size() <= MAX_GAME_PLAYED) {
                    if (!mapChooseData.containsKey(list.get(j).get(k)) &&
                            (lastDifficulty || db.gameDao().getWWHDataByData(userId, list.get(j).get(k)).getWinStreak() < 3) &&
                            db.appDao().getWordIfContain('%' + list.get(j).get(k) + '%').size() > 0) {

                        words = db.appDao().getWordIfContain('%' + list.get(j).get(k) + '%');
                        for (int i = 0; i < 3; i++)
                            Collections.shuffle(words);
                        int rand = random.nextInt(words.size());

                        mapChooseData.put(list.get(j).get(k), words.get(rand));
                        words.clear();
                        list.get(j).remove(k);
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

    private void readInstructionAndWord(boolean instruction) {
        delay = true;
        String str = "";
        int timeDelay = 500;
        if (instruction) {
            str = "Trouve la lettre ou la syllabe manquante du mot . ";
            timeDelay += 2000;
        }
        str += mapChooseData.get(goodAnswer).getWord();
        MyTextToSpeech.speachText(this, str);

        new Handler().postDelayed(() -> {
            delay = false;
        }, timeDelay);
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
                readInstructionAndWord(false);
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
        float average = (float)nbWin / (float)MAX_GAME_PLAYED;
        if (average == 1)
            return 3;
        else if (average >= 0.75)
            return 2;
        return 1;
    }

    private void replay() {
        word.setText(mapChooseData.get(goodAnswer).getWord());
        word.setTextColor(Color.GREEN);

        playSound(true);
        textAnimation(true);

        updateDataInDb();

        gamePlayed++;
        delay = true;

        new Handler().postDelayed(() -> {
            delay = false;

            if (gamePlayed <= MAX_GAME_PLAYED) {
                nbTry = 0;
                initListAnswer();
                setLayoutContent();
                word.setTextColor(Color.BLACK);
                readInstructionAndWord(false);
                resetButton();
            } else {
                int stars = starsNumber();
                addGameResultLogInDb(stars);
                Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
                intent.putExtra("starsNumber", stars);
                startActivity(intent);
                finish();
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

    private void updateDataInDb() {
        WordWithHoleData data = db.gameDao().getWWHDataByData(userId, goodAnswer);
        data.setLastUsed(1);
        boolean win;
        if (nbTry == 0) {
            data.setWin(data.getWin() + 1);
            data.setWinStreak(data.getWinStreak() + 1);
            data.setLoseStreak(0);
            win = true;
            nbWin++;
        } else {
            data.setLose(data.getLose() + 1);
            data.setLoseStreak(data.getLoseStreak() + 1);
            data.setWinStreak(0);
            win = false;
        }
        db.gameDao().updateWWHData(data);

        GameLog gameLog = new GameLog(
                gameId,
                -1,
                data.getDataId(),
                win,
                nbTry);
        db.gameLogDao().insertGameLog(gameLog);
    }

    private void addGameResultLogInDb(int stars) {
        GameResultLog gameResultLog = new GameResultLog(gameId, -1, userId, stars);
        db.gameLogDao().insertGameResultLog(gameResultLog);
    }

    private void verifyAnswer(Button answer, int numAnswer) {
        MyVibrator.vibrate(WordWithHole.this, 35);
        if (answer.getText() == goodAnswer) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            setWordAndAddDelay(numAnswer);
            answer.setEnabled(false);
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            switch (v.getId()) {
                case R.id.ic_help_wordWithHole:
                    readInstructionAndWord(true);
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
}