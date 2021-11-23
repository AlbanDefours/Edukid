package fr.dut.ptut2021.adapters.game;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderGame extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;

    public MyViewHolderGame(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.gameImage);
        name = itemView.findViewById(R.id.gameName);
    }
}
