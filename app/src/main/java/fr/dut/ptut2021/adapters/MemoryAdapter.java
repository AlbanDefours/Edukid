package fr.dut.ptut2021.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Card;

public class MemoryAdapter extends BaseAdapter {

    Context  context;
    List<Card> listCard;

    public MemoryAdapter(Context context, List<Card> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    /*@NonNull
    @Override
    public MyViewHolderMemory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderMemory(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMemory holder, int position) {
        holder.element.setImageResource(listCard.get(position).getDrawableImage());
        holder.pattern.setImageResource(R.drawable.patternimg);
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }*/

    @Override
    public int getCount() {
        return listCard.size();
    }

    @Override
    public Object getItem(int i) {
        return listCard.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        MyViewHolderMemory holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card, null);
            holder = new MyViewHolderMemory(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolderMemory) convertView.getTag();
        }

        
        holder.pattern.setImageResource(R.drawable.patternimg);
        holder.pattern.setImageResource(R.drawable.patternimg);

        holder.element.setImageResource(listCard.get(i).getDrawableImage());
        holder.element.setMaxWidth("");

        return convertView;
    }
}