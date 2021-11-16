package fr.dut.ptut2021.adapters.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderUser extends RecyclerView.ViewHolder {

    ImageView avatar;
    TextView name;

    public MyViewHolderUser(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.userAvatar);
        name = itemView.findViewById(R.id.userName);
    }
}
