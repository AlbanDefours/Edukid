package fr.dut.ptut2021.adapters.subgame;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderSubGame extends RecyclerView.ViewHolder {
    ImageView image, lock;
    TextView name;

    public MyViewHolderSubGame(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.subGameImage);
        name = itemView.findViewById(R.id.subGameName);
        lock = itemView.findViewById(R.id.subGameImageLock);
    }
}
