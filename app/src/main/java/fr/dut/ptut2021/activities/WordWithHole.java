package fr.dut.ptut2021.activities;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private TextView word;
    private ImageView image;
    private Button answer1 , answer2, answer3;
    private List<ArrayList<String>> listWord;   //Mot plein, Mot à trou, Bonne réponse
    private List<Integer> listImage;
    private List<String> listAnswer;
    private List<String> listSyllable;
    private int indWordChoose;
    private CreateDatabase db;
    private final int nbGamePlayedMax = 5;
    private int nbGamePlayed = 1;
    private final int nbTryMax = 2;
    private int nbTry = 0;
    private int wrongAnswerCheck = 0;
    private boolean delay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);

        initGame();

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
    }

    private void initGame() {
        initListWord();
        initListSyllable();

        indWordChoose = getRandomWord();
        initListAnswer();

        initializeLayout();
        setLayoutContent();
    }

    private void initListWord() {
        listWord = new ArrayList<>();

        listWord.add(new ArrayList<>());
        listWord.get(0).add("Avion");
        listWord.get(0).add("Avi__");
        listWord.get(0).add("ON");

        listWord.add(new ArrayList<>());
        listWord.get(1).add("Maison");
        listWord.get(1).add("Mais__");
        listWord.get(1).add("ON");
    }

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
        image.setImageResource(R.drawable.wordwithhole_avion);
        word.setText(listWord.get(indWordChoose).get(1));
        answer1.setText(listAnswer.get(0));
        answer2.setText(listAnswer.get(1));
        answer3.setText(listAnswer.get(2));
    }

    //Renvoie l'indice de la liste listWord
    private int getRandomWord() {
        int rand = (int)(Math.random() * listWord.size());
        return rand;
    }

    private void initializeLayout() {
        word = findViewById(R.id.word_wordWithHole);
        image = findViewById(R.id.imageWord_wordWithHole);
        answer1 = findViewById(R.id.buttonAnswer1_wordWithHole);
        answer2 = findViewById(R.id.buttonAnswer2_wordWithHole);
        answer3 = findViewById(R.id.buttonAnswer3_wordWithHole);
    }

    private void replay() {
        word.setText(listWord.get(indWordChoose).get(0));
        nbGamePlayed++;
        delay = true;
        new Handler().postDelayed(() -> {
            delay = false;
            if (nbGamePlayed <= nbGamePlayedMax) {
                nbTry = 0;
                initGame();
                answer1.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer2.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer3.setBackgroundColor(Color.parseColor("#00BCD4"));
            } else {
                Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (!delay) {
            switch (v.getId()) {
                case R.id.buttonAnswer1_wordWithHole:
                    if (answer1.getText() == listWord.get(indWordChoose).get(2)) {
                        answer1.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer1.setBackgroundColor(Color.RED);
                        if (wrongAnswerCheck != 1) {
                            nbTry++;
                            if (nbTry >= nbTryMax) {
                                replay();
                            }
                        }
                    }
                    break;

                case R.id.buttonAnswer2_wordWithHole:
                    if (answer2.getText() == listWord.get(indWordChoose).get(2)) {
                        answer2.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer2.setBackgroundColor(Color.RED);
                        if (wrongAnswerCheck != 2) {
                            nbTry++;
                            if (nbTry >= nbTryMax) {
                                replay();
                            }
                        }
                    }
                    break;

                case R.id.buttonAnswer3_wordWithHole:
                    if (answer3.getText() == listWord.get(indWordChoose).get(2)) {
                        answer3.setBackgroundColor(Color.GREEN);
                        replay();
                    } else {
                        answer3.setBackgroundColor(Color.RED);
                        if (wrongAnswerCheck != 3) {
                            nbTry++;
                            if (nbTry >= nbTryMax) {
                                replay();
                            }
                        }
                    }
                    break;
            }
        }
    }

}
