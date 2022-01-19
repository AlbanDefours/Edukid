package fr.dut.ptut2021.adapters.subgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.database.app.Game;

public class SubGameAdapter extends RecyclerView.Adapter<MyViewHolderSubGame> {

    Context  context;
    List<Game> listGame;
    List<Boolean> locks;

    public SubGameAdapter(Context context, List<Game> listGame, List<Boolean> locks) {
        this.context = context;
        this.listGame = listGame;
        this.locks = locks;
    }

    @NonNull
    @Override
    public MyViewHolderSubGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderSubGame(LayoutInflater.from(context).inflate(R.layout.item_subgame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSubGame holder, int position) {
        holder.name.setText(listGame.get(position).getGameName());
        holder.image.setImageResource(listGame.get(position).getGameImage());
        if(locks.get(position)){
            holder.lock.setVisibility(View.VISIBLE);
        }
        else{
            holder.lock.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }
}
