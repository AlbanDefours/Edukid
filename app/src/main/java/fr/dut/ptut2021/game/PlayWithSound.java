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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.PlayWithSoundData;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.models.database.log.GameResultLog;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyTextToSpeech;
import fr.dut.ptut2021.utils.MyVibrator;

public class PlayWithSound extends AppCompatActivity implements View.OnClickListener {

    private CreateDatabase db;
    private ImageView btnSound, help;
    private TextView goodAnswerView;
    private boolean delay = false, isAnswerFalseWord = false;
    private List<String> listAnswer, listChooseResult;
    private Button answer1, answer2, answer3;
    private String themeName;
    private String[] alphabetTab, syllableTab;
    private final Button[] listButton = new Button[3];
    private final int MAX_GAME_PLAYED = 5;
    private int userId, gameId, gamePlayed = 1, nbTry = 0, answerFalse = 0, nbrStars = 0, answerFalseWord = 0, difficulty = 1;
    private Random random = new Random();
    private String articleTheme;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_sound);

        GlobalUtils.verifyIfSoundIsOn(this);

        getSharedPref();
        initDatabase();
        readInstruction(false);
        initializeLayout();
        fillListChooseResult();
        initGame();
        addOnClickListener();
    }

    private void getSharedPref() {
        userId = MySharedPreferences.getUserId(this);
        themeName = MySharedPreferences.getThemeName(this);
        gameId = MySharedPreferences.getGameId(this);
    }

    private void initDatabase() {
        db = CreateDatabase.getInstance(PlayWithSound.this);

        alphabetTab = getResources().getStringArray(R.array.alphabet);
        for (String letter : alphabetTab)
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, letter, "Lettres", 1, 0));

        syllableTab = getResources().getStringArray(R.array.syllable);
        for (String syllable : syllableTab)
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, syllable, "Lettres", 2, 0));

        for (int i = 1; i < 10; i++)
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, Integer.toString(i), "Chiffres", 1, 0));
    }

    private void initializeLayout() {
        goodAnswerView = findViewById(R.id.goodAnswer_playWithSound);
        btnSound = findViewById(R.id.btnSound_playWithSound);
        answer1 = findViewById(R.id.buttonAnswer1_playWithSound);
        answer2 = findViewById(R.id.buttonAnswer2_playWithSound);
        answer3 = findViewById(R.id.buttonAnswer3_playWithSound);
        help = findViewById(R.id.ic_help_playWithSound);
        listButton[0] = answer1;
        listButton[1] = answer2;
        listButton[2] = answer3;
    }

    private void initGame() {
        initListAnswer();
        setLayoutContent();
        new Handler().postDelayed(this::readTheAnswer, 200);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillListChooseResult() {
        List<PlayWithSoundData> listData = new ArrayList<>(db.gameDao().getAllPWSDataByTheme(userId, themeName));
        listChooseResult = new ArrayList<>();
        int maxDifficulty = db.gameDao().getPWSDataMaxDifficulty(userId, themeName);
        List<List<List<String>>> listAllData = new ArrayList<>();

        for (int i = 0; i < maxDifficulty; i++)
            listAllData.add(new ArrayList<>());

        for (int dif = 1; dif <= maxDifficulty; dif++) {
            List<String> listDataNeverUsed = db.gameDao().getAllPWSDataLastUsed(listData, dif, -1);
            List<String> listDataNotUsed = db.gameDao().getAllPWSDataLastUsed(listData, dif, 0);
            List<String> listDataUsed = db.gameDao().getAllPWSDataLastUsed(listData, dif, 1);

            listAllData.get(dif-1).add(listDataNeverUsed);
            listAllData.get(dif-1).add(listDataNotUsed);
            listAllData.get(dif-1).add(listDataUsed);

            Collections.shuffle(listAllData.get(dif-1).get(1));
            Collections.shuffle(listAllData.get(dif-1).get(2));

            Log.e("APPLOG", "List Dif " + dif + " (-1): " + listAllData.get(dif-1).get(0));
            Log.e("APPLOG", "List Dif " + dif + " (0): " + listAllData.get(dif-1).get(1));
            Log.e("APPLOG", "List Dif " + dif + " (1): " + listAllData.get(dif-1).get(2));

            if (listChooseResult.size() <= MAX_GAME_PLAYED) {
                fillListChooseResult(listAllData, dif);
                difficulty = dif;
            }
        }
        db.gameDao().updateAllPWSDataLastUsed(userId);
    }


    private void fillListChooseResult(List<List<List<String>>> listData, int difficulty) {
        for (int j = 0; j < listData.get(difficulty-1).size(); j++) {
            for (int k = 0; k < listData.get(difficulty-1).get(j).size(); k++) {

                if (listChooseResult.size() <= MAX_GAME_PLAYED && listData.get(difficulty-1).get(j).size() > 0) {
                    String answer = listData.get(difficulty-1).get(j).get(k);
                    if (!listChooseResult.contains(answer) &&
                            (difficulty == listData.size() || db.gameDao().getPWSDataByResult(userId, answer).getWinStreak() < 3)) {
                        listChooseResult.add(answer);
                    }
                }
            }
        }
    }


    private void readTheAnswer() {
        MyTextToSpeech.speachText(this, "Trouve " + articleTheme + themeName.toLowerCase() + " : " + listChooseResult.get(gamePlayed - 1));
    }

    private void readInstruction(boolean help) {
        int sum = 0;
        int limit = 2;
        if (!help) {
            for (int star : db.gameLogDao().getAllGameResultLogStarsLimit(userId, gameId, limit))
                sum += star;
        }
        if (help || sum <= limit) {
            delay = true;
            articleTheme = themeName.equals("Chiffres") ? "le " : "la ";
            String s = themeName.equals("Chiffres") ? "un " : "une ";
            MyTextToSpeech.speachText(PlayWithSound.this, "Dans cet exercice tu vas entendre " + s + themeName.toLowerCase() + " et tu dois " + articleTheme + " retrouver");
            new Handler().postDelayed(() -> {
                delay = false;
            }, 3000);
        }
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();
        listAnswer.add(listChooseResult.get(gamePlayed - 1));

        while (listAnswer.size() < 3) {
            if (themeName.equals("Chiffres")) {
                int rand = random.nextInt(9)+1;
                if (!listAnswer.contains(Integer.toString(rand))) {
                    listAnswer.add(Integer.toString(rand));
                }
            } else if (listAnswer.get(0).length() == 1) {
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

    private void setLayoutContent() {
        goodAnswerView.setVisibility(View.INVISIBLE);
        goodAnswerView.setText(listChooseResult.get(gamePlayed - 1));
        for (int i = 0; i < 3; i++)
            listButton[i].setText(listAnswer.get(i));
    }

    private void addOnClickListener() {
        btnSound.setOnClickListener(this);
        for (int i = 0; i < 3; i++)
            listButton[i].setOnClickListener(this);
        help.setOnClickListener(this);
    }

    private void playSound(boolean isgoodAnswerView) {
        if (isgoodAnswerView)
            MyMediaPlayer.playSound(this, R.raw.correct_answer);
        else
            MyMediaPlayer.playSound(this, R.raw.wrong_answer);
    }

    private void setWordAndAddDelay() {
        delay = true;
        playSound(false);

        delay = false;
        nbTry++;
        int MAX_TRY = 2;
        if (nbTry >= MAX_TRY) {
            delay = true;
            new Handler().postDelayed(() -> {
                for (int i = 0; i < 3; i++) {
                    if (listButton[i].getText().equals(listChooseResult.get(gamePlayed - 1)))
                        listButton[i].setBackgroundColor(Color.GREEN);
                }
                replay();
            }, 2000);
        }else
            new Handler().postDelayed(this::readTheAnswer, 500);
    }

    private void displayAnswer(boolean showAnswer) {
        if (showAnswer) {
            btnSound.setVisibility(View.INVISIBLE);
            goodAnswerView.setVisibility(View.VISIBLE);
        } else {
            btnSound.setVisibility(View.VISIBLE);
            goodAnswerView.setVisibility(View.INVISIBLE);
        }
    }

    private void replay() {
        playSound(true);
        updateDataInDb();
        gamePlayed++;
        answerFalseWord = 0;
        delay = true;
        displayAnswer(true);
        new Handler().postDelayed(() -> {
            delay = false;
            if (gamePlayed <= MAX_GAME_PLAYED) {
                nbTry = 0;
                displayAnswer(false);
                initGame();
                for (int i = 0; i < 3; i++) {
                    listButton[i].setBackgroundColor(Color.parseColor("#00BCD4"));
                    listButton[i].setEnabled(true);
                }
            } else {
                if (0 <= answerFalse && answerFalse < 2)//Nombre etoile
                    nbrStars = 3;
                else if (isAnswerFalseWord)
                    nbrStars = 1;
                else
                    nbrStars = 2;
                addGameResultLogInDb(nbrStars);
                GlobalUtils.startResultPage(PlayWithSound.this, nbrStars);
            }
        }, 3000);
    }

    private void updateDataInDb() {
        PlayWithSoundData data = db.gameDao().getPWSDataByResult(userId,
                listChooseResult.get(gamePlayed - 1));
        data.setLastUsed(1);
        boolean win;
        if (nbTry == 0) {
            data.setWin(data.getWin() + 1);
            data.setWinStreak(data.getWinStreak() + 1);
            data.setLoseStreak(0);
            win = true;
        } else {
            data.setLose(data.getLose() + 1);
            data.setLoseStreak(data.getLoseStreak() + 1);
            data.setWinStreak(0);
            win = false;
        }
        db.gameDao().updatePWSData(data);

        GameLog gameLog = new GameLog(gameId, -1, data.getDataId(), win, nbTry);
        db.gameLogDao().insertGameLog(gameLog);
    }

    private void addGameResultLogInDb(int stars) {
        GameResultLog gameResultLog = new GameResultLog(gameId, -1, userId, stars, difficulty);
        db.gameLogDao().insertGameResultLog(gameResultLog);
    }

    private void verifyAnswer(Button answer) {
        if (answer.getText() == listChooseResult.get(gamePlayed - 1)) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            answerFalse++;
            answerFalseWord++;
            if (answerFalseWord == 2)
                isAnswerFalseWord = true;
            setWordAndAddDelay();
            answer.setEnabled(false);
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            MyVibrator.vibrate(PlayWithSound.this, 35);
            switch (v.getId()) {
                case R.id.buttonAnswer1_playWithSound:
                    verifyAnswer(answer1);
                    break;

                case R.id.buttonAnswer2_playWithSound:
                    verifyAnswer(answer2);
                    break;

                case R.id.buttonAnswer3_playWithSound:
                    verifyAnswer(answer3);
                    break;

                case R.id.btnSound_playWithSound:
                    readTheAnswer();
                    break;

                case R.id.ic_help_playWithSound:
                    readInstruction(true);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalUtils.stopAllSound(PlayWithSound.this);
    }
}