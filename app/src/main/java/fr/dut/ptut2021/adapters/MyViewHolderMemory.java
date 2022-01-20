package fr.dut.ptut2021.adapters;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.dut.ptut2021.R;

public class MyViewHolderMemory extends RecyclerView.ViewHolder {

    boolean hidden;
    View viewCard;
    View interieurCardChiffre, interieurCardLettre;
    ArrayList<ImageView> elements = new ArrayList<>();
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
        viewCard = itemView.findViewById(R.id.viewCard);
        interieurCardChiffre = itemView.findViewById(R.id.interieurCardChiffre);
        interieurCardLettre = itemView.findViewById(R.id.interieurCardLettre);
        elements.add(itemView.findViewById(R.id.elementMemory1));
        elements.add(itemView.findViewById(R.id.elementMemory2));
        elements.add(itemView.findViewById(R.id.elementMemory3));
        elements.add(itemView.findViewById(R.id.elementMemory4));
        elements.add(itemView.findViewById(R.id.elementMemory5));
        elements.add(itemView.findViewById(R.id.elementMemory6));
        elements.add(itemView.findViewById(R.id.elementMemory7));
        elements.add(itemView.findViewById(R.id.elementMemory8));
        elements.add(itemView.findViewById(R.id.elementMemory9));

        pattern = itemView.findViewById(R.id.patternImgMemory);
        background = itemView.findViewById(R.id.backgroundImgMemory);
        returnCard = itemView.findViewById(R.id.returnImgMemory);


        sato0.setDuration(250);
        sato1.setDuration(250);

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
                if (hidden) {
                    viewCard.setAnimation(null);
                    showImageReturnCard();
                    returnCard.startAnimation(sato1);
                } else {
                    returnCard.setAnimation(null);
                    showImageViewCard();
                    viewCard.startAnimation(sato1);
                }
            }
        });

    }

    public void showImageReturnCard() {
            returnCard.setVisibility(View.VISIBLE);
            viewCard.setVisibility(View.INVISIBLE);
    }

    public void showImageViewCard() {
        returnCard.setVisibility(View.INVISIBLE);
        viewCard.setVisibility(View.VISIBLE);
    }
}