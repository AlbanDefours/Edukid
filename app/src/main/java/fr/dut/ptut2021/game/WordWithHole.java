package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.GameMenu;
import fr.dut.ptut2021.activities.ThemeMenu;
import fr.dut.ptut2021.activities.UserMenu;
import fr.dut.ptut2021.database.CreateDatabase;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private CreateDatabase db;
    private TextView word;
    private ImageView image;
    private Button answer1 , answer2, answer3;
    private List<ArrayList<String>> listWord;   //Mot plein, Mot à trou, Bonne réponse
    private List<Integer> listImage;
    private List<String> listAnswer, listSyllable;
    private final int MAX_GAME_PLAYED = 5, MAX_TRY = 2;
    private int indWordChoose, gamePlayed = 1, nbTry = 0, wrongAnswerCheck = 0;
    private boolean delay = false;
    private MediaPlayer mpGoodAnswer, mpWrongAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);

        initGame();
        initSoundEffect();

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
    }

    private void initGame() {
        initListWord();
        initListSyllable();

        indWordChoose = (int)(Math.random() * listWord.size());
        initListAnswer();

        initializeLayout();
        setLayoutContent();
    }

    private void initSoundEffect() {
        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);
    }

    private void initListWord() {
        listWord = new ArrayList<>();
        listImage = new ArrayList<>();

        listWord.add(new ArrayList<>());
        listWord.get(0).add("AVION");
        listWord.get(0).add("AVI__");
        listWord.get(0).add("ON");
        listImage.add(R.drawable.wordwithhole_avion);

        listWord.add(new ArrayList<>());
        listWord.get(1).add("MAISON");
        listWord.get(1).add("MAIS__");
        listWord.get(1).add("ON");
        listImage.add(R.drawable.wordwithhole_maison);
    }

    // Check si un lettre est déjà dans la réponse finale (plus dur)
    private void initListSyllable() {
        listSyllable = new ArrayList<>();
        listSyllable.add("ON");
        listSyllable.add("MA");
        listSyllable.add("TU");
        listSyllable.add("TI");
        listSyllable.add("DU");
        listSyllable.add("SA");
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();

        listAnswer.add(listWord.get(indWordChoose).get(2));
        while (listAnswer.size() < 3) {
            int rand = (int) (Math.random() * listSyllable.size());
            if (!listAnswer.contains(listSyllable.get(rand))) {
                listAnswer.add(listSyllable.get(rand));
            }
        }

        for (int i = 0; i < 4; i++) {
            Collections.shuffle(listAnswer);
        }
    }

    private void setLayoutContent() {
        image.setImageResource(listImage.get(indWordChoose));
        word.setText(listWord.get(indWordChoose).get(1));
        answer1.setText(listAnswer.get(0));
        answer2.setText(listAnswer.get(1));
        answer3.setText(listAnswer.get(2));
    }

    private void initializeLayout() {
        word = findViewById(R.id.word_wordWithHole);
        image = findViewById(R.id.imageWord_wordWithHole);
        answer1 = findViewById(R.id.buttonAnswer1_wordWithHole);
        answer2 = findViewById(R.id.buttonAnswer2_wordWithHole);
        answer3 = findViewById(R.id.buttonAnswer3_wordWithHole);
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

    private void playSound(boolean goodAnswer) {
        if (goodAnswer)
            mpGoodAnswer.start();
        else
            mpWrongAnswer.start();
    }

    private String concatWrongAnswer(int indAnswer) {
        String s = listWord.get(indWordChoose).get(1);
        s = s.replace("__", listAnswer.get(indAnswer - 1));
        System.out.println(s);
        return s;
    }

    private void setWordAndAddDelay(int indWrongAnswer) {
        delay = true;
        word.setText(concatWrongAnswer(indWrongAnswer));
        word.setTextColor(Color.RED);
        playSound(false);
        textAnimation(false);

        new Handler().postDelayed(() -> {
            delay = false;
            word.setText(listWord.get(indWordChoose).get(1));
            word.setTextColor(Color.BLACK);
        }, 2000);

        if (wrongAnswerCheck != indWrongAnswer) {
            nbTry++;
            if (nbTry >= MAX_TRY) {
                delay = true;
                new Handler().postDelayed(() -> {
                    if (answer1.getText() == listWord.get(indWordChoose).get(2))
                        answer1.setBackgroundColor(Color.GREEN);
                    if (answer2.getText() == listWord.get(indWordChoose).get(2))
                        answer2.setBackgroundColor(Color.GREEN);
                    if (answer3.getText() == listWord.get(indWordChoose).get(2))
                        answer3.setBackgroundColor(Color.GREEN);
                    replay();
                }, 2000);
            }
        }

    }

    private void replay() {
        word.setText(listWord.get(indWordChoose).get(0));
        word.setTextColor(Color.GREEN);
        playSound(true);
        textAnimation(true);
        gamePlayed++;
        delay = true;
        new Handler().postDelayed(() -> {
            delay = false;
            if (gamePlayed <= MAX_GAME_PLAYED) {
                nbTry = 0;
                initGame();
                word.setTextColor(Color.BLACK);
                answer1.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer2.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer3.setBackgroundColor(Color.parseColor("#00BCD4"));
            } else {
                finish();
            }
        }, 3000);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        if (!delay) {
            String goodAnswer = listWord.get(indWordChoose).get(2);
            switch (v.getId()) {
                case R.id.buttonAnswer1_wordWithHole:
                    if (answer1.getText() == goodAnswer) {
                        answer1.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer1.setBackgroundColor(Color.RED);
                        setWordAndAddDelay(1);
                    }
                    break;

                case R.id.buttonAnswer2_wordWithHole:
                    if (answer2.getText() == goodAnswer) {
                        answer2.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer2.setBackgroundColor(Color.RED);
                        setWordAndAddDelay(2);
                    }
                    break;

                case R.id.buttonAnswer3_wordWithHole:
                    if (answer3.getText() == goodAnswer) {
                        answer3.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer3.setBackgroundColor(Color.RED);
                        setWordAndAddDelay(3);
                    }
                    break;
            }
        }
    }

}
