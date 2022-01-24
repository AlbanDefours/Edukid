package fr.dut.ptut2021.adapters.subgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.database.app.SubGame;

public class SubGameAdapter extends RecyclerView.Adapter<MyViewHolderSubGame> {

    Context  context;
    List<SubGame> listSubGame;
    List<Boolean> locks;

    public SubGameAdapter(Context context, List<SubGame> listSubGame, List<Boolean> locks) {
        this.context = context;
        this.listSubGame = listSubGame;
        this.locks = locks;
    }

    @NonNull
    @Override
    public MyViewHolderSubGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderSubGame(LayoutInflater.from(context).inflate(R.layout.item_subgame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSubGame holder, int position) {
        holder.name.setText(listSubGame.get(position).getSubGameName());
        holder.image.setImageResource(listSubGame.get(position).getSubGameImage());

        if(locks.get(position))
            holder.lock.setVisibility(View.VISIBLE);
        else
            holder.lock.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return listSubGame.size();
    }
}
