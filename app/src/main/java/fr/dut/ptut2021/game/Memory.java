package fr.dut.ptut2021.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.MemoryAdapter;
import fr.dut.ptut2021.models.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Memory extends AppCompatActivity {
    private ArrayList<Card> listCard;
    private int idLastCardReturn=-1;
    int numColumns;

   /* public Memory(ArrayList<Card> listCard){

        display();
    }*/

    private void shuffle(){
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
    }

    public void returnCard(int idCard,MemoryAdapter memoryAdapter) throws InterruptedException, IOException {
        if(listCard.get(idCard).isHidden()){
            listCard.get(idCard).setHidden(false);
            memoryAdapter.setCard(idCard);
            memoryAdapter.notifyDataSetChanged();

            new Handler().postDelayed(() -> {
                if (idLastCardReturn == -1) {
                    idLastCardReturn = idCard;
                } else {
                    if (idCard != idLastCardReturn && listCard.get(idLastCardReturn).getValue() == listCard.get(idCard).getValue()) {

                        idLastCardReturn = -1;
                    } else {
                        listCard.get(idCard).setHidden(true);
                        listCard.get(idLastCardReturn).setHidden(true);
                        memoryAdapter.setCard(idCard);
                        memoryAdapter.notifyDataSetChanged();
                        memoryAdapter.setCard(idLastCardReturn);
                        memoryAdapter.notifyDataSetChanged();
                        idLastCardReturn = -1;
                    }
                }
            }, 2000);
        }
    }

    public void display(MemoryAdapter memoryAdapter) throws IOException, InterruptedException {
        int compteur=1;

        System.out.println("Memory : ");
        for (Card card : listCard){
            if(!card.isHidden()){
                System.out.print(card.getValue()+" ");
            }
            else
                System.out.print("X ");

            if(compteur%numColumns ==0)
                System.out.println("   __   "+compteur);

            compteur++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        listCard = new ArrayList<>();
        listCard.add(new Card("1",R.drawable.one));
        listCard.add(new Card("2",R.drawable.two));
        listCard.add(new Card("3",R.drawable.three));
        listCard.add(new Card("4",R.drawable.four));
        listCard.add(new Card("5",R.drawable.five));
        listCard.add(new Card("6",R.drawable.six));

        int size = listCard.size();
        for(int i=0;i<size;i++){
            this.listCard.add( new Card(listCard.get(i)));
        }
        shuffle();


        GridView gridView = findViewById(R.id.gridview_memory);
        numColumns = (int) Math.sqrt(listCard.size());
        gridView.setNumColumns(numColumns);

        MemoryAdapter memoryAdapter = new MemoryAdapter(getApplicationContext(), listCard,numColumns);
        gridView.setAdapter(memoryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                try {
                    returnCard(position,memoryAdapter);
                }
                catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /*recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MemoryAdapter(getApplicationContext(), listCard));*/
    }
}
