package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.game.Memory;
import fr.dut.ptut2021.game.PlayWithSound;
import fr.dut.ptut2021.game.WordWithHole;

public class ResultGamePage extends AppCompatActivity {

    private int starsNb;
    private String gameName, themeName;
    private MediaPlayer mpNiceTry;
    private ImageView star1, star2, star3, exit, replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game_page);

        getNbStars();
        getGameThemeName();
        initializeView();
        initSoundEffect();
        starsNumber(starsNb);

        exit.setOnClickListener(v -> {
            finish();
        });

        replay.setOnClickListener(v -> {
            findGame();
        });
    }

    private void getNbStars() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
            starsNb = bundle.getInt("starsNumber", 2);
        else
            starsNb = 3;
    }

    private void getGameThemeName() {
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        themeName = settings.getString("themeName", "");
        gameName = settings.getString("gameName", "");
    }

    private void initializeView() {
        star1 = findViewById(R.id.icon_star1);
        star2 = findViewById(R.id.icon_star2);
        star3 = findViewById(R.id.icon_star3);
        exit = findViewById(R.id.exitButton_ResultPage);
        replay = findViewById(R.id.retryButton_ResultPage);
    }

    private void initSoundEffect() {
        mpNiceTry = MediaPlayer.create(this, R.raw.kids_cheering);
    }

    private void starsNumber(int nbStars) {
        ImageView[] tabStars = {star1, star2, star3};
        for (int i = 0; i < nbStars; i++) {
            int finalI = i;
            new Handler().postDelayed(() -> {
                tabStars[finalI].setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Swing).duration(800).playOn(tabStars[finalI]);
            }, 800L * i);
        }

        new Handler().postDelayed(() -> {
            YoYo.with(Techniques.Tada).duration(1000).repeat(2).playOn(findViewById(R.id.text_felicitation));
            mpNiceTry.start();
        }, 600L * nbStars + 500);
    }

    private void findGame() {
        System.out.println(gameName);
        switch (gameName) {
            case "Ecoute":
                startActivity(new Intent().setClass(getApplicationContext(), PlayWithSound.class));
                break;
            case "Dessine":
                //startActivity(new Intent().setClass(getApplicationContext(), DrawOnIt.class));
                break;
            case "Memory":
                startActivity(new Intent().setClass(getApplicationContext(), Memory.class));
                break;
            case "Mot à trou":
                startActivity(new Intent().setClass(getApplicationContext(), WordWithHole.class));
                break;
        }
        finish();
    }
}