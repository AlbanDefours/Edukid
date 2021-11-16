package fr.dut.ptut2021.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Card;

public class MemoryAdapter extends RecyclerView.Adapter<MyViewHolderMemory> {

    Context  context;
    List<Card> listCard;

    public MemoryAdapter(Context context, List<Card> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    @NonNull
    @Override
    public MyViewHolderMemory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderMemory(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMemory holder, int position) {
        holder.element.setImageResource(listCard.get(position).getDrawableImage());
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }
}