package fr.dut.ptut2021.adapters;


import android.content.Context;
import android.content.SharedPreferences;
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
            SharedPreferences settings = context.getSharedPreferences("MyPref", 0);

            String themeName = settings.getString("themeName", "");
            if(themeName.equals("Chiffres") && !listCard.get(i).getValue().equals("1") && !isChiffre(listCard.get(i).getDrawableImage())){
                for (int k=0;k<Integer.parseInt(listCard.get(i).getValue());k++){
                    holder.elements.get(k).setImageResource(listCard.get(i).getDrawableImage());
                }
            }
            else{
                holder.elements.get(0).setImageResource(listCard.get(i).getDrawableImage());
            }

            //
            double width = (1094.0 + 20) / numColumns;
            double height = width * (1684.0 / 1094) + 20;
            double sizeElement = width -50;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) width, (int) height);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            holder.viewCard.setLayoutParams(layoutParams);

            holder.returnCard.setLayoutParams(layoutParams);

            layoutParams = new RelativeLayout.LayoutParams((int) sizeElement, (int) sizeElement);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            holder.interieurCard.setLayoutParams(layoutParams);


            if(themeName.equals("Chiffres") && !listCard.get(i).getValue().equals("1") && !isChiffre(listCard.get(i).getDrawableImage())){
                System.out.println("c'est VRAIIIIIIII");
                switch (listCard.get(i).getValue()){
                    case "2":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement);
                        holder.elements.get(0).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement);
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);
                        break;
                    case "3":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins((int)(sizeElement/4),0,0,0);
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins(0,(int)(sizeElement/2),0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement/2),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);
                        break;
                    case "4":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins(0,(int)(sizeElement/2),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/2);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement/2),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);
                        break;
                    case "5":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/4),(int)(sizeElement*0.33),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins(0,(int)(sizeElement*0.66),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.66),0,0);
                        holder.elements.get(4).setLayoutParams(layoutParams);
                        break;
                    case "6":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins(0,(int)(sizeElement*0.33),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.33),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins(0,(int)(sizeElement*0.66),0,0);
                        holder.elements.get(4).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/3);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.66),0,0);
                        holder.elements.get(5).setLayoutParams(layoutParams);
                        break;
                    case "7":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins((int)(sizeElement/4),(int)(sizeElement*0.165),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins(0,(int)(sizeElement*0.33),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.33),0,0);
                        holder.elements.get(4).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins(0,(int)(sizeElement*0.66),0,0);
                        holder.elements.get(5).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/3.5));
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.66),0,0);
                        holder.elements.get(6).setLayoutParams(layoutParams);
                        break;
                    case "8":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins((int)(sizeElement/4),(int)(sizeElement*0.165),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins(0,(int)(sizeElement*0.33),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.33),0,0);
                        holder.elements.get(4).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins((int)(sizeElement/4),(int)(sizeElement*0.495),0,0);
                        holder.elements.get(5).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins(0,(int)(sizeElement*0.66),0,0);
                        holder.elements.get(6).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) sizeElement/4);
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.66),0,0);
                        holder.elements.get(7).setLayoutParams(layoutParams);
                        break;
                    case "9":
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        holder.elements.get(0).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins((int)(sizeElement/2),0,0,0);
                        holder.elements.get(1).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins(0,(int)(sizeElement*0.25),0,0);
                        holder.elements.get(2).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.25),0,0);
                        holder.elements.get(3).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins((int)(sizeElement/4),(int)(sizeElement*0.375),0,0);
                        holder.elements.get(4).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins(0,(int)(sizeElement*0.5),0,0);
                        holder.elements.get(5).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.5),0,0);
                        holder.elements.get(6).setLayoutParams(layoutParams);

                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins(0,(int)(sizeElement*0.75),0,0);
                        holder.elements.get(7).setLayoutParams(layoutParams);
                        layoutParams = new RelativeLayout.LayoutParams((int) sizeElement/2, (int) (sizeElement/4.5));
                        layoutParams.setMargins((int)(sizeElement/2),(int)(sizeElement*0.75),0,0);
                        holder.elements.get(8).setLayoutParams(layoutParams);
                        break;
                }
            }
            else{
                System.out.println("c'est FAUUUUUUX");
                layoutParams = new RelativeLayout.LayoutParams((int) sizeElement, (int) sizeElement);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                holder.elements.get(0).setLayoutParams(layoutParams);
            }


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

    private boolean isChiffre(int drawableImage){
        if(drawableImage!= R.drawable.one && drawableImage!= R.drawable.two && drawableImage!= R.drawable.three && drawableImage!= R.drawable.four && drawableImage!= R.drawable.five && drawableImage!= R.drawable.six && drawableImage!= R.drawable.seven && drawableImage!= R.drawable.eight && drawableImage!= R.drawable.nine){
            return false;
        }
        else
            return true;
    }

}