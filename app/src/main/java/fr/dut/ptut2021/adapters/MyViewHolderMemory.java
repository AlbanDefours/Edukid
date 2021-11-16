package fr.dut.ptut2021.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderMemory extends RecyclerView.ViewHolder {

    ImageView element;
    ImageView pattern;
    ImageView background;

    public MyViewHolderMemory(@NonNull View itemView) {
        super(itemView);
        element = itemView.findViewById(R.id.elementMemory);
        pattern = itemView.findViewById(R.id.patternImgMemory);
        //background = itemView.findViewById(R.id.backgroundImgMemory);
    }
}