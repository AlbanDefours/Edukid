package fr.dut.ptut2021.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import fr.dut.ptut2021.R;

public class GameMenu extends AppCompatActivity {

    private ImageView imageA;
    private ImageView imageB;
    RelativeLayout rl;

    private ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
            0.5f);
    private ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
            0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        imageA = findViewById(R.id.ivA);
        imageB = findViewById(R.id.ivB);
        rl = findViewById(R.id.root);

        shwoImageA();

        sato0.setDuration(500);
        sato1.setDuration(500);

        sato0.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (imageA.getVisibility() == View.VISIBLE) {
                    imageA.setAnimation(null);
                    showImageB();
                    imageB.startAnimation(sato1);
                }else{
                    imageB.setAnimation(null);
                    shwoImageA();
                    imageA.startAnimation(sato1);
                }
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageA.getVisibility() == View.VISIBLE) {
                    imageA.startAnimation(sato0);
                }else{
                    imageB.startAnimation(sato0);
                }
            }
        });
    }

    private void shwoImageA(){
        imageA.setVisibility(View.VISIBLE);
        imageB.setVisibility(View.INVISIBLE);
    }

    private void showImageB(){
        imageA.setVisibility(View.INVISIBLE);
        imageB.setVisibility(View.VISIBLE);
    }
}