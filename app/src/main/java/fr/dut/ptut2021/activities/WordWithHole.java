package fr.dut.ptut2021.activities;

import android.os.Bundle;
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

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private TextView word;
    private ImageView image;
    private Button answer1 , answer2, answer3;
    private List<ArrayList<String>> listWord;   //Mot plein, Mot à trou, Bonne réponse
    private List<String> listAnswer;
    private List<String> listSyllable;
    private CreateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);

        initListWord();
        initListSyllable();

        int indWordChoose = getRandomWord();
        initListAnswer(indWordChoose);

        initializeLayout();
        setLayoutContent(indWordChoose);
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

    private void initListAnswer(int ind) {
        listAnswer = new ArrayList<>();

        listAnswer.add(listWord.get(ind).get(2));
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

    private void setLayoutContent(int ind) {
        image.setImageResource(R.drawable.wordwithhole_avion);
        word.setText(listWord.get(ind).get(1));
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

    @Override
    public void onClick(View v) {

    }

}
