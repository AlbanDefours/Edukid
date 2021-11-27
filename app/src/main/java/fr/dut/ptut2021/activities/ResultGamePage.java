package fr.dut.ptut2021.activities;

import android.content.Intent;
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
import fr.dut.ptut2021.game.WordWithHole;

//TODO refaire la page qaund on aura choisi les sons
public class ResultGamePage extends AppCompatActivity {

    private String gameName, themeName;
    private MediaPlayer mpStarsSound, mpNiceTry;
    private ImageView star1, star2, star3, exit, replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game_page);

        getGameName();
        initializeView();
        initSoundEffect();
        new Handler().postDelayed(() -> {
            starsNumber(3);
        }, 1500);

        exit.setOnClickListener(v -> {
            finish();
        });

        replay.setOnClickListener(v -> {
            findGame();
        });
    }

    private void getGameName() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            gameName = bundle.getString("gameName", " ");
            themeName = bundle.getString("themeName", " ");
        }
    }

    private void initializeView() {
        star1 = findViewById(R.id.icon_star1);
        star2 = findViewById(R.id.icon_star2);
        star3 = findViewById(R.id.icon_star3);
        exit = findViewById(R.id.exitButton_ResultPage);
        replay = findViewById(R.id.retryButton_ResultPage);
    }

    private void initSoundEffect() {
        mpStarsSound = MediaPlayer.create(this, R.raw.correct_answer);
        mpNiceTry = MediaPlayer.create(this, R.raw.kids_cheering);
    }

    private void starsNumber(int nbStars) {
        switch (nbStars) {
            case 1:
                star1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Swing).duration(800).playOn(star1);
                mpStarsSound.start();
                break;

            case 2:
                star1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Swing).duration(800).playOn(star1);
                mpStarsSound.start();
                new Handler().postDelayed(() -> {
                    star2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.Swing).duration(800).playOn(star2);
                    mpStarsSound.start();
                }, 1200);
                break;

            case 3:
                star1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Swing).duration(800).playOn(star1);
                mpStarsSound.start();
                new Handler().postDelayed(() -> {
                    star2.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.Swing).duration(800).playOn(star2);
                    //mpStarsSound.stop();
                    //mpStarsSound.reset();
                    mpStarsSound.start();
                    new Handler().postDelayed(() -> {
                        star3.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Swing).duration(800).playOn(star3);
                        //mpStarsSound.stop();
                        //mpStarsSound.reset();
                        mpStarsSound.start();
                    }, 1500);
                }, 1500);
                break;
        }
        new Handler().postDelayed(() -> {
            YoYo.with(Techniques.Tada).duration(1000).repeat(2).playOn(findViewById(R.id.text_felicitation));
            mpNiceTry.start();
        }, 1200L * nbStars + 500);
    }

    private void startGame(Intent intent) {
        intent.putExtra("themeName", themeName);
        startActivity(intent);
        finish();
    }

    private void findGame() {
        switch (gameName) {
            case "WordWithHole":
                startGame(new Intent().setClass(getApplicationContext(), WordWithHole.class));
                break;
            case "Memory":
                startGame(new Intent().setClass(getApplicationContext(), Memory.class));
                break;
        }
    }
}