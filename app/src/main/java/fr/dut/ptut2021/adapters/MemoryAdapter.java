package fr.dut.ptut2021.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Card;

public class MemoryAdapter extends BaseAdapter {

    Context  context;
    List<Card> listCard;
    ArrayList<Integer> position;
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
                convertView =(View) LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
                holder = new MyViewHolderMemory(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolderMemory) convertView.getTag();
            }

            holder.returnCard.setImageResource(R.drawable.imgreturn);
            holder.pattern.setImageResource(R.drawable.patternimg);
            holder.background.setImageResource(R.drawable.backgroundmemory);
            holder.element.setImageResource(listCard.get(i).getDrawableImage());

            //
            double width = (1094.0 + 20) / numColumns;
            double height = width * (1684.0 / 1094) + 20;
            double sizeElement = width * (800.0 / 1094);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) width, (int) height);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            holder.viewCard.setLayoutParams(layoutParams);

            holder.returnCard.setLayoutParams(layoutParams);

            layoutParams = new RelativeLayout.LayoutParams((int) sizeElement, (int) sizeElement);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            holder.element.setLayoutParams(layoutParams);

            holder.hidden = listCard.get(i).isHidden();


        if(position== null || position.isEmpty()){

            if (listCard.get(i).isHidden()) {
                holder.showImageReturnCard();

            }else{
                holder.showImageViewCard();
            }

        }
        else {
            for (int j=0;j<position.size();j++){
                if(position.get(j) == i) {
                    if (listCard.get(i).isHidden()) {
                        holder.viewCard.startAnimation(holder.sato0);

                    } else {
                        holder.returnCard.startAnimation(holder.sato0);
                    }
                }
            }
        }
        return convertView;
    }

    public void setCard(ArrayList<Integer> position){
        this.position = position;
    }

}