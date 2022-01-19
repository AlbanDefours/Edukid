package fr.dut.ptut2021.adapters.subgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;

public class SubGameAdapter extends RecyclerView.Adapter<MyViewHolderSubGame> {

    Context  context;
    List<SubGame> listGame;

    public SubGameAdapter(Context context, List<SubGame> listGame) {
        this.context = context;
        this.listGame = listGame;
    }

    @NonNull
    @Override
    public MyViewHolderSubGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderSubGame(LayoutInflater.from(context).inflate(R.layout.item_subgame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSubGame holder, int position) {
        holder.name.setText(listGame.get(position).getSubGameName());
        holder.image.setImageResource(listGame.get(position).getSubGameImage());
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }
}
