package fr.dut.ptut2021.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Card;

public class MemoryAdapter extends BaseAdapter {

    Context  context;
    List<Card> listCard;
    int numColumns;
    MyViewHolderMemory holder;



    public MemoryAdapter(Context context, List<Card> listCard, int numColumns) {
        this.context = context;
        this.listCard = listCard;
        this.numColumns = numColumns;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card,parent, false);
            holder = new MyViewHolderMemory(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolderMemory) convertView.getTag();
        }
        holder.returnCard.setImageResource(R.drawable.imgreturn);
        holder.pattern.setImageResource(R.drawable.patternimg);
        holder.background.setImageResource(R.drawable.backgroundmemory);
        holder.element.setImageResource(listCard.get(i).getDrawableImage());

        if(listCard.get(i).isHidden()){
            holder.showImagePattern();
        }
        else{

            holder.shwoImageReturnCard();
        }

        //animation(i);


        double width = (1094.0+20)/numColumns;
        double height = width*(1684.0/1094)+20;
        double sizeElement =  width*(800.0/1094);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)width,(int)height);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        holder.pattern.setLayoutParams(layoutParams);
        holder.background.setLayoutParams(layoutParams);
        holder.returnCard.setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams((int)sizeElement,(int)sizeElement);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        holder.element.setLayoutParams(layoutParams);

        return convertView;
    }


    public void animation(int i){
        if (holder.returnCard.getVisibility() == View.VISIBLE && !listCard.get(i).isHidden()) {
            holder.returnCard.startAnimation(holder.sato0);
        }else if(holder.pattern.getVisibility() == View.VISIBLE && listCard.get(i).isHidden()){
            holder.pattern.startAnimation(holder.sato0);
            holder.background.startAnimation(holder.sato0);
            holder.element.startAnimation(holder.sato0);
        }

    }

}