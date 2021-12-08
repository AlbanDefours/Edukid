package fr.dut.ptut2021.game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.stats.game.WordWithHoleData;

public class WordWithHole extends AppCompatActivity implements View.OnClickListener {

    private CreateDatabase db;
    private int userId;
    private TextView word;
    private ImageView image;
    private Button answer1 , answer2, answer3;
    private List<WordWithHoleData> listData;
    private List<Integer> listChooseWord;
    private List<String> listAnswer;
    private String goodAnswer;
    private final int MAX_GAME_PLAYED = 4, MAX_TRY = 2;
    private int gamePlayed = 1, nbTry = 0;
    private boolean delay = false;
    private MediaPlayer mpGoodAnswer, mpWrongAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_with_hole);

        db = CreateDatabase.getInstance(WordWithHole.this);
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        userId = settings.getInt("userId", 0);
        fillDatabase();

        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);

        initGame();
        initListAnswer();
        setLayoutContent();

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
    }

    private void fillDatabase() {
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "AVION", "ON", R.drawable.wordwithhole_avion));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "MAISON", "ON", R.drawable.wordwithhole_maison));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "POULE", "OU", R.drawable.wordwithhole_poule));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "BOUCHE", "OU", R.drawable.wordwithhole_bouche));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "LIVRE", "LI", R.drawable.wordwithhole_livre));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "VACHE", "VA", R.drawable.wordwithhole_vache));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "TOMATE", "MA", R.drawable.wordwithhole_tomate));
        db.gameDao().insertWWHData(new WordWithHoleData(userId, "TOMATE", "TO", R.drawable.wordwithhole_tomate));
    }

    private void initGame() {
        listData = new ArrayList<>();
        listData.addAll(db.gameDao().getAllWWHData(userId));
        listChooseWord = new ArrayList<>();
        List<Integer> listDataNotUsed = db.gameDao().getAllWWHDataLastUsed(listData, false);

        if (listDataNotUsed.size() > MAX_GAME_PLAYED) {
            while (listChooseWord.size() < MAX_GAME_PLAYED) {
                int rand = (int)(Math.random() * listDataNotUsed.size());
                if (!listChooseWord.contains(listDataNotUsed.get(rand))) {
                    listChooseWord.add(listDataNotUsed.get(rand));
                }
            }
        }
        else {
            List<Integer> listDataUsed = db.gameDao().getAllWWHDataLastUsed(listData, true);
            listChooseWord.addAll(listDataNotUsed);
            for (int i = listDataNotUsed.size(); i < MAX_GAME_PLAYED; i++) {
                int rand = (int)(Math.random() * listDataUsed.size());
                if (!listChooseWord.contains(listDataUsed.get(rand))) {
                    listChooseWord.add(listDataUsed.get(rand));
                }
            }
        }

        while (listChooseWord.size() < MAX_GAME_PLAYED) {
            int rand = (int)(Math.random() * listData.size());
            if (!listChooseWord.contains(rand)) {
                listChooseWord.add(rand);
            }
        }
        db.gameDao().updateAllWWHDataLastUsed(userId);
    }

    private void initListAnswer() {
        listAnswer = new ArrayList<>();

        listAnswer.add(listData.get(listChooseWord.get(gamePlayed -1)).getSyllable());
        while (listAnswer.size() < 3) {
            int rand = (int) (Math.random() * listData.size());
            if (!listAnswer.contains(listData.get(rand).getSyllable())) {
                listAnswer.add(listData.get(rand).getSyllable());
            }
        }

        for (int i = 0; i < 4; i++) {
            Collections.shuffle(listAnswer);
        }

        goodAnswer = listData.get(listChooseWord.get(gamePlayed -1)).getSyllable();
    }

    private void setLayoutContent() {
        word = findViewById(R.id.word_wordWithHole);
        image = findViewById(R.id.imageWord_wordWithHole);
        answer1 = findViewById(R.id.buttonAnswer1_wordWithHole);
        answer2 = findViewById(R.id.buttonAnswer2_wordWithHole);
        answer3 = findViewById(R.id.buttonAnswer3_wordWithHole);

        word.setText(holeTheWord());
        image.setImageResource(listData.get(listChooseWord.get(gamePlayed -1)).getImage());
        answer1.setText(listAnswer.get(0));
        answer2.setText(listAnswer.get(1));
        answer3.setText(listAnswer.get(2));
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
            mpGoodAnswer.start();
        else
            mpWrongAnswer.start();
    }

    private String concatWrongAnswer(int indAnswer) {
        String s = listData.get(listChooseWord.get(gamePlayed -1)).getWord();
        s = s.replace(listData.get(listChooseWord.get(gamePlayed -1)).getSyllable(), listAnswer.get(indAnswer - 1));
        return s;
    }

    private String holeTheWord() {
        String s = listData.get(listChooseWord.get(gamePlayed -1)).getWord();
        s = s.replace(listData.get(listChooseWord.get(gamePlayed -1)).getSyllable(), "__");
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
            word.setText(holeTheWord());
            word.setTextColor(Color.BLACK);
        }, 2000);

        nbTry++;
        if (nbTry >= MAX_TRY) {
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
        if (button.getText() == listData.get(listChooseWord.get(gamePlayed - 1)).getSyllable())
            button.setBackgroundColor(Color.GREEN);
    }

    private void replay() {
        word.setText(listData.get(listChooseWord.get(gamePlayed -1)).getWord());
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
                answer1.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer2.setBackgroundColor(Color.parseColor("#00BCD4"));
                answer3.setBackgroundColor(Color.parseColor("#00BCD4"));
            } else {
                Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
                intent.putExtra("starsNumber", 3);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void updateDataInDb() {
        WordWithHoleData data = db.gameDao().getWWHData(
                userId,
                listData.get(listChooseWord.get(gamePlayed -1)).getWord(),
                listData.get(listChooseWord.get(gamePlayed -1)).getSyllable());
        data.setLastUsed(true);
        if (nbTry == 0) {
            data.setWin(data.getWin() +1);
            data.setWinStreak(data.getWinStreak() +1);
            data.setLoseStreak(0);
        }
        else {
            data.setLose(data.getLose() +1);
            data.setLoseStreak(data.getLoseStreak() +1);
            data.setWinStreak(0);
        }
        db.gameDao().updateWWHData(data);
    }

    private void verifyAnswer(Button answer, int numAnswer) {
        if (answer.getText() == goodAnswer) {
            answer.setBackgroundColor(Color.GREEN);
            replay();
        } else {
            answer.setBackgroundColor(Color.RED);
            setWordAndAddDelay(numAnswer);
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
            }
        }
    }
}