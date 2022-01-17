package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.stats.GameLog;
import fr.dut.ptut2021.models.databse.stats.game.PlayWithSoundData;

public class PlayWithSound extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private Vibrator vibe;
    private CreateDatabase db;
    private ImageView btnSound;
    private TextView goodAnswer;
    private boolean delay = false, isAnswerFalseWord = false;
    private List<String> listAnswer;
    private List<Integer> listChooseResult;
    private TextToSpeech textToSpeech;
    private List<PlayWithSoundData> listData;
    private Button answer1, answer2, answer3;
    private String themeName;
    private MediaPlayer mpGoodAnswer, mpWrongAnswer;
    private final Button[] listButton = new Button[3];
    private final int MAX_GAME_PLAYED = 5;
    private int userId, gamePlayed = 1, nbTry = 0, answerFalse = 0, nbrStars = 0, answerFalseWord = 0;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_sound);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        getSharedPref();
        initDatabase();
        initializeLayout();
        fillListChooseResult(themeName);
        initGame();
        initSoundEffect();
        addOnClickListener();

        textToSpeech = new TextToSpeech(this, this);
    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibe.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibe.vibrate(35);
    }

    private void getSharedPref() {
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        userId = settings.getInt("userId", 0);
        themeName = settings.getString("themeName", "");
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
        new Handler().postDelayed(this::readTheAnswer, 1200);
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
        if (themeName.equals("Lettres"))
            speak("Trouve la lettre : " + listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
        else if (themeName.equals("Chiffres"))
            speak("Trouve le chiffre : " + listData.get(listChooseResult.get(gamePlayed - 1)).getResult());
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

    private void initSoundEffect() {
        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);
    }

    private void addOnClickListener() {
        btnSound.setOnClickListener(this);
        for (int i = 0; i < 3; i++)
            listButton[i].setOnClickListener(this);
    }

    private void playSound(boolean isGoodAnswer) {
        if (isGoodAnswer)
            mpGoodAnswer.start();
        else
            mpWrongAnswer.start();
    }

    private void setWordAndAddDelay() {
        delay = true;
        playSound(false);

        new Handler().postDelayed(() -> {
            delay = false;
            readTheAnswer();
        }, 2000);

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
        }
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
        answerFalseWord=0;
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
                intent.putExtra("starsNumber", nbrStars);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void speak(String texte) {
        HashMap<String, String> onlineSpeech = new HashMap<>();
        onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if(texte.equals("Trouve la lettre : Y") || texte.equals("Trouve la lettre : y"))
                textToSpeech.speak("Trouve la lettre : igrec", TextToSpeech.QUEUE_FLUSH, null, null);
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
    }

    private void updateDataInDb() {

        PlayWithSoundData data = db.gameDao().getPWSDataByResult(
                userId,
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

        GameLog gameLog = new GameLog(
                "PlayWithSound",
                data.getDataId(),
                win,
                nbTry);
        db.gameLogDao().insertGameLog(gameLog);

    }

    private void verifyAnswer(Button answer) {
        if (answer.getText() == listData.get(listChooseResult.get(gamePlayed - 1)).getResult()) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            answerFalse++;
            answerFalseWord++;
            if(answerFalseWord == 2)
                isAnswerFalseWord = true;
            Log.d("WILL", "" + answerFalseWord);
            setWordAndAddDelay();
            answer.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)
            textToSpeech.setLanguage(Locale.FRANCE);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            vibrate();
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
}