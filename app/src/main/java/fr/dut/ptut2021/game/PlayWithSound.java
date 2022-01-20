package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
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
    private ImageView btnSound;
    private TextView goodAnswer;
    private boolean delay = false, isAnswerFalseWord = false;
    private List<String> listAnswer;
    private List<Integer> listChooseResult;
    private List<PlayWithSoundData> listData;
    private Button answer1, answer2, answer3;
    private String themeName;
    private final Button[] listButton = new Button[3];
    private final int MAX_GAME_PLAYED = 5;
    private int userId, gamePlayed = 1, nbTry = 0, answerFalse = 0, nbrStars = 0, answerFalseWord = 0;
    private Random random = new Random();
    private String articleTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_sound);

        GlobalUtils.verifyIfSoundIsOn(this);

        getSharedPref();
        articleTheme = themeName.equals("Chiffres") ? "le " : "la ";
        String s = themeName.equals("Chiffres") ? "un " : "une ";
        MyTextToSpeech.speachText(getApplicationContext(), "Dans cet exercice tu vas entendre " + s + themeName.toLowerCase() + " et tu dois " + articleTheme + " retrouver");

        initDatabase();
        initializeLayout();
        fillListChooseResult(themeName);
        initGame();
        addOnClickListener();
    }

    private void getSharedPref() {
        userId = MySharedPreferences.getUserId(this);
        themeName = MySharedPreferences.getThemeName(this);
    }

    private void initDatabase() {
        db = CreateDatabase.getInstance(PlayWithSound.this);

        String[] alphabetList = getResources().getStringArray(R.array.alphabet);
        for (String letter : alphabetList) {
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, letter, "Lettres", 1, 0));
        }

        String[] syllableList = getResources().getStringArray(R.array.syllable);
        for (String syllable : syllableList) {
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, syllable, "Lettres", 2, 0));
        }

        for (int i = 1; i < 10; i++) {
            db.gameDao().insertPWSData(new PlayWithSoundData(db.gameDao().getPWSMaxId() + 1, userId, Integer.toString(i), "Chiffres", 1, 0));
        }
    }

    private void initializeLayout() {
        goodAnswer = findViewById(R.id.goodAnswer_playWithSound);
        btnSound = findViewById(R.id.btnSound_playWithSound);
        answer1 = findViewById(R.id.buttonAnswer1_playWithSound);
        answer2 = findViewById(R.id.buttonAnswer2_playWithSound);
        answer3 = findViewById(R.id.buttonAnswer3_playWithSound);
        listButton[0] = answer1;
        listButton[1] = answer2;
        listButton[2] = answer3;
    }

    private void initGame() {
        initListAnswer();
        setLayoutContent();
        new Handler().postDelayed(this::readTheAnswer, 200);
    }

    //TODO Choisi pour le moment Result uniquement en fonction de LastUsed
    private void fillListChooseResult(String theme) {
        listData = new ArrayList<>(db.gameDao().getAllPWSDataByTheme(userId, theme, 1));
        listChooseResult = new ArrayList<>();
        List<Integer> listDataNeverUsed = db.gameDao().getAllPWSDataLastUsed(listData, -1);
        List<Integer> listDataNotUsed = db.gameDao().getAllPWSDataLastUsed(listData, 0);
        List<Integer> listDataUsed = db.gameDao().getAllPWSDataLastUsed(listData, 1);

        for (int i = 0; i < MAX_GAME_PLAYED; i++) {
            if (!listDataNeverUsed.isEmpty() && !listChooseResult.contains(listDataNeverUsed.get(0))) {
                listChooseResult.add(listDataNeverUsed.get(0));
                listDataNeverUsed.remove(0);
            } else if (!listDataNotUsed.isEmpty()) {
                int rand = random.nextInt(listDataNotUsed.size());
                if (!listChooseResult.contains(listDataNotUsed.get(rand))) {
                    listChooseResult.add(listDataNotUsed.get(rand));
                    listDataNotUsed.remove(rand);
                }
            } else {
                int rand = random.nextInt(listDataUsed.size());
                if (!listChooseResult.contains(listDataUsed.get(rand))) {
                    listChooseResult.add(listDataUsed.get(rand));
                    listDataUsed.remove(rand);
                }
            }
        }
        db.gameDao().updateAllPWSDataLastUsed(userId);
    }

    private void readTheAnswer() {
        MyTextToSpeech.speachText(this, "Trouve " + articleTheme + themeName.toLowerCase() + " : " + listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();

        listAnswer.add(listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
        while (listAnswer.size() < 3) {
            int rand = random.nextInt(listData.size());
            if (!listAnswer.contains(listData.get(rand).getResult()))
                listAnswer.add(listData.get(rand).getResult());
        }

        for (int i = 0; i < 4; i++) {
            Collections.shuffle(listAnswer);
        }
    }

    private void setLayoutContent() {
        goodAnswer.setVisibility(View.INVISIBLE);
        goodAnswer.setText(listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
        for (int i = 0; i < 3; i++)
            listButton[i].setText(listAnswer.get(i));
    }

    private void addOnClickListener() {
        btnSound.setOnClickListener(this);
        for (int i = 0; i < 3; i++)
            listButton[i].setOnClickListener(this);
    }

    private void playSound(boolean isGoodAnswer) {
        if (isGoodAnswer)
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
                    if (listButton[i].getText().equals(listData.get(listChooseResult.get(gamePlayed - 1)).getResult()))
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
            goodAnswer.setVisibility(View.VISIBLE);
        } else {
            btnSound.setVisibility(View.VISIBLE);
            goodAnswer.setVisibility(View.INVISIBLE);
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
                Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
                if (0 <= answerFalse && answerFalse < 2)//Nombre etoile
                    nbrStars = 3;
                else if (isAnswerFalseWord)
                    nbrStars = 1;
                else
                    nbrStars = 2;
                addGameResultLogInDb(nbrStars);
                intent.putExtra("starsNumber", nbrStars);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void updateDataInDb() {
        PlayWithSoundData data = db.gameDao().getPWSDataByResult(userId,
                listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
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

        GameLog gameLog = new GameLog("PlayWithSound", data.getDataId(), win, nbTry);
        db.gameLogDao().insertGameLog(gameLog);
    }

    private void addGameResultLogInDb(int stars) {
        GameResultLog gameResultLog = new GameResultLog("PlayWithSound", userId, stars);
        db.gameLogDao().insertGameResultLog(gameResultLog);
    }

    private void verifyAnswer(Button answer) {
        if (answer.getText() == listData.get(listChooseResult.get(gamePlayed - 1)).getResult()) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            answerFalse++;
            answerFalseWord++;
            if (answerFalseWord == 2)
                isAnswerFalseWord = true;
            Log.d("WILL", "" + answerFalseWord);
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
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyMediaPlayer.stop();
        MyTextToSpeech.stop();
    }
}