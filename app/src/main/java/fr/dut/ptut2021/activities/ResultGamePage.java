package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyVibrator;

public class ResultGamePage extends AppCompatActivity {

    private int starsNb = 0;
    private String gameName, themeName;
    private ImageView star1, star2, star3, exit, replay;
    private Handler handlerStars, handlerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game_page);

        handlerStars = new Handler();
        handlerTitle = new Handler();

        getNbStars();
        getGameThemeName();
        initializeView();
        starsNumber(starsNb);

        exit.setOnClickListener(v -> {
            stopAllHandler();
            MyVibrator.vibrate(this, 35);
            finish();
        });

        replay.setOnClickListener(v -> {
            stopAllHandler();
            MyVibrator.vibrate(this, 35);
            GlobalUtils.startGame(this, gameName, false, false);
            finish();
        });
    }

    private void stopAllHandler(){
        handlerStars.removeCallbacksAndMessages(null);
        handlerTitle.removeCallbacksAndMessages(null);
        MyMediaPlayer.stop();
    }

    private void getNbStars() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
            starsNb = bundle.getInt("starsNumber", 1);

        if (starsNb > 3 || starsNb < 1)
            starsNb = 2;
    }

    private void getGameThemeName() {
        themeName = MySharedPreferences.getThemeName(this);
        gameName = MySharedPreferences.getGameName(this);
        if (gameName.equals("Memory"))
            gameName = "SubMemory";
    }

    private void initializeView() {
        star1 = findViewById(R.id.icon_star1);
        star2 = findViewById(R.id.icon_star2);
        star3 = findViewById(R.id.icon_star3);
        exit = findViewById(R.id.exitButton_ResultPage);
        replay = findViewById(R.id.retryButton_ResultPage);
    }

    private void starsNumber(int nbStars) {
        ImageView[] tabStars = {star1, star2, star3};
        switch (nbStars) {
            case 1:
                MyMediaPlayer.playSound(this, R.raw.one_star);
                break;
            case 2:
                MyMediaPlayer.playSound(this, R.raw.two_stars);
                break;
            case 3:
                MyMediaPlayer.playSound(this, R.raw.three_stars);
                break;
        }

        for (int i = 0; i < nbStars; i++) {
            int finalI = i;
            handlerStars.postDelayed(() -> {
                tabStars[finalI].setImageResource(R.drawable.icon_star);
                YoYo.with(Techniques.Swing).duration(800).playOn(tabStars[finalI]);
            }, 800L * i);
        }

        handlerTitle.postDelayed(() -> {
            YoYo.with(Techniques.Tada).duration(1000).repeat(2).playOn(findViewById(R.id.text_felicitation));
            MyMediaPlayer.playSound(this, R.raw.kids_cheering);
        }, 800L * nbStars + 200);
    }
}