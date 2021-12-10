package fr.dut.ptut2021.adapters.theme;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;

public class MyViewHolderTheme extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name;

    public MyViewHolderTheme(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.themeImage);
        name = itemView.findViewById(R.id.themeName);
    }
}
