package fr.dut.ptut2021.adapters.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Game;

public class GameAdapter extends RecyclerView.Adapter<MyViewHolderGame> {

    Context  context;
    List<Game> listGame;

    public GameAdapter(Context context, List<Game> listGame) {
        this.context = context;
        this.listGame = listGame;
    }

    @NonNull
    @Override
    public MyViewHolderGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderGame(LayoutInflater.from(context).inflate(R.layout.item_game, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderGame holder, int position) {
        holder.name.setText(listGame.get(position).getName());
        holder.image.setImageResource(listGame.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }
}
