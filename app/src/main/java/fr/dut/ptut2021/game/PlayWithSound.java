package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.stats.game.WordWithHoleData;

public class PlayWithSound extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private ImageView btnSound;
    private TextView goodAnswer;
    private Button answer1, answer2, answer3;
    private CreateDatabase db;
    private TextToSpeech textToSpeech;
    private boolean delay = false;
    private List<String> listAnswer;
    private String goodAnswerString;
    private List<WordWithHoleData> listData;
    private final int MAX_GAME_PLAYED = 5, MAX_TRY = 2;
    private MediaPlayer mpGoodAnswer, mpWrongAnswer;
    private int userId, gamePlayed = 1, indWordChoose, wrongAnswerCheck = 0, nbTry = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_sound);

        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        userId = settings.getInt("userId", 0);
        db = CreateDatabase.getInstance(PlayWithSound.this);

        fillDatabase();
        initGame();

        initSoundEffect();

        btnSound.setOnClickListener(this);
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);

        textToSpeech = new TextToSpeech(this, this);

        new Handler().postDelayed(() -> {
            speak("La lettre : A !"); //TODO lire la lettre / le chiffre
        }, 1000);
    }

    private void fillDatabase() {
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "AVION", "ON", R.drawable.wordwithhole_avion));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "MAISON", "ON", R.drawable.wordwithhole_maison));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "POULE", "OU", R.drawable.wordwithhole_poule));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "BOUCHE", "OU", R.drawable.wordwithhole_bouche));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "LIVRE", "LI", R.drawable.wordwithhole_livre));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "VACHE", "VA", R.drawable.wordwithhole_vache));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "TOMATE", "MA", R.drawable.wordwithhole_tomate));
        db.gameDao().insertWWHStats(new WordWithHoleData(userId, "TOMATE", "TO", R.drawable.wordwithhole_tomate));
    }

    private void initGame() {
        listData = db.gameDao().getAllWWHStats(userId);
        indWordChoose = (int) (Math.random() * listData.size());
        goodAnswerString = listData.get(indWordChoose).getSyllable();
        initListAnswer();
        initializeLayout();
        setLayoutContent();
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();

        listAnswer.add(listData.get(indWordChoose).getSyllable());
        while (listAnswer.size() < 3) {
            int rand = (int) (Math.random() * listData.size());
            if (!listAnswer.contains(listData.get(rand).getSyllable())) {
                listAnswer.add(listData.get(rand).getSyllable());
            }
        }

        for (int i = 0; i < 4; i++) {
            Collections.shuffle(listAnswer);
        }
    }

    private void initializeLayout() {
        goodAnswer = findViewById(R.id.goodAnswer_playWithSound);
        btnSound = findViewById(R.id.btnSound_playWithSound);
        answer1 = findViewById(R.id.buttonAnswer1_playWithSound);
        answer2 = findViewById(R.id.buttonAnswer2_playWithSound);
        answer3 = findViewById(R.id.buttonAnswer3_playWithSound);
    }

    private void setLayoutContent() {
        goodAnswer.setVisibility(View.GONE);
        goodAnswer.setText(listData.get(indWordChoose).getWord());
        answer1.setText(listAnswer.get(0));
        answer2.setText(listAnswer.get(1));
        answer3.setText(listAnswer.get(2));
    }

    private void initSoundEffect() {
        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);
    }

    private void playSound(boolean goodAnswer) {
        if (goodAnswer)
            mpGoodAnswer.start();
        else
            mpWrongAnswer.start();
    }

    private void setWordAndAddDelay(int indWrongAnswer) {
        delay = true;
        playSound(false);

        new Handler().postDelayed(() -> {
            delay = false;
        }, 2000);

        if (wrongAnswerCheck != indWrongAnswer) {
            nbTry++;
            if (nbTry >= MAX_TRY) {
                delay = true;
                new Handler().postDelayed(() -> {
                    if (answer1.getText() == listData.get(indWordChoose).getSyllable())
                        answer1.setBackgroundColor(Color.GREEN);
                    if (answer2.getText() == listData.get(indWordChoose).getSyllable())
                        answer2.setBackgroundColor(Color.GREEN);
                    if (answer3.getText() == listData.get(indWordChoose).getSyllable())
                        answer3.setBackgroundColor(Color.GREEN);
                    replay();
                }, 2000);
            }
        }
    }

    private void replay() {
        playSound(true);
        gamePlayed++;
        delay = true;
        new Handler().postDelayed(() -> {
            delay = false;
            if (gamePlayed <= MAX_GAME_PLAYED) {
                nbTry = 0;
                initGame();
                answer1.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer2.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer3.setBackgroundColor(Color.parseColor("#00BCD4"));
            } else {
                Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void speak(String texte) {
        HashMap<String, String> onlineSpeech = new HashMap<>();
        onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
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
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.FRANCE);
        }
    }

    private void verifyAnswer(Button answer, int x){
        if (answer.getText() == goodAnswerString) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            setWordAndAddDelay(x);
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            switch (v.getId()) {
                case R.id.buttonAnswer1_wordWithHole:
                    verifyAnswer(answer1, 1);
                    break;

                case R.id.buttonAnswer2_wordWithHole:
                    verifyAnswer(answer2, 2);
                    break;

                case R.id.buttonAnswer3_wordWithHole:
                    verifyAnswer(answer3, 3);
                    break;

                case R.id.btnSound_playWithSound:
                    speak("La lettre : A !"); //TODO text
                    break;
            }
        }
    }
}