package fr.dut.ptut2021.adapters;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderMemory extends RecyclerView.ViewHolder {

    ImageView element;
    ImageView pattern;
    ImageView returnCard;
    ImageView background;
    ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
            0.5f);
    ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT,
            0.5f);

    public MyViewHolderMemory(@NonNull View itemView) {
        super(itemView);
        element = itemView.findViewById(R.id.elementMemory);
        pattern = itemView.findViewById(R.id.patternImgMemory);
        background = itemView.findViewById(R.id.backgroundImgMemory);
        returnCard = itemView.findViewById(R.id.returnImgMemory);


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
                if (pattern.getVisibility() == View.VISIBLE) {
                    pattern.setAnimation(null);
                    background.setAnimation(null);
                    element.setAnimation(null);
                    showImageReturnCard();
                    returnCard.startAnimation(sato1);
                } else {
                    returnCard.setAnimation(null);
                    showImagePattern();
                    pattern.startAnimation(sato1);
                    background.startAnimation(sato1);
                    element.startAnimation(sato1);
                }
            }
        });

    }

    public void showImageReturnCard() {
            returnCard.setVisibility(View.VISIBLE);
            pattern.setVisibility(View.INVISIBLE);
            element.setVisibility(View.INVISIBLE);
            background.setVisibility(View.INVISIBLE);
    }

    public void showImagePattern() {
        returnCard.setVisibility(View.INVISIBLE);
        pattern.setVisibility(View.VISIBLE);
        element.setVisibility(View.VISIBLE);
        background.setVisibility(View.VISIBLE);
    }
}