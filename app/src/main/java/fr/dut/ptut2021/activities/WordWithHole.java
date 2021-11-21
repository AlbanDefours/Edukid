package fr.dut.ptut2021.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private TextView word;
    private ImageView image;
    private Button answer1 , answer2, answer3;
    private Map<String, String> listWord;
    private CreateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);

        initListWord();
        initializeLayout();
        setLayoutContent();
    }

    private void initListWord() {
        listWord = new HashMap<>();
        listWord.put("Avion", "Avi__");
        listWord.put("Maison", "Mais__");
    }

    //Renvoie l'indice de la map listWord
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

    private void setLayoutContent() {
        image.setImageResource(R.drawable.wordwithhole_avion);
        word.setText(listWord.get("Avion"));
        answer1.setText("ON");
        answer2.setText("TA");
        answer3.setText("LU");
    }

    @Override
    public void onClick(View v) {

    }

}
