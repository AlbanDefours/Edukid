package fr.dut.ptut2021.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.adapters.MemoryAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Memory extends AppCompatActivity {

    private ArrayList<Card> listCard;
    private int idLastCardReturn=-1;
    int numColumns;
    boolean isClicked=false;
    private MediaPlayer mpGoodAnswer;
    private MediaPlayer mpWrongAnswer;
    private CreateDatabase db;

    private void shuffle(){
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
    }

    public void returnCard(int idCard,MemoryAdapter memoryAdapter) {
        if(listCard.get(idCard).isHidden()){
            ArrayList<Integer> returnableCards=new ArrayList<>();
            isClicked=true;
            listCard.get(idCard).setHidden(false);
            returnableCards.add(idCard);
            memoryAdapter.setCard(returnableCards);
            memoryAdapter.notifyDataSetChanged();


                if (idLastCardReturn == -1) {
                    idLastCardReturn = idCard;
                    isClicked=false;
                } else {
                    new Handler().postDelayed(() -> {
                        if (idCard != idLastCardReturn && listCard.get(idLastCardReturn).getValue() == listCard.get(idCard).getValue()) {
                            mpGoodAnswer.start();
                            idLastCardReturn = -1;
                        } else {
                            listCard.get(idCard).setHidden(true);
                            listCard.get(idLastCardReturn).setHidden(true);
                            mpWrongAnswer.start();
                            returnableCards.add(idLastCardReturn);
                            memoryAdapter.setCard(returnableCards);
                            memoryAdapter.notifyDataSetChanged();

                            idLastCardReturn = -1;
                        }
                        isClicked=false;
                    }, 1000);
                }
        }
    }

    private boolean isWin(){
        for(int i=0;i<listCard.size();i++){
            if(listCard.get(i).isHidden()){
                return false;
            }
        }
        int ptMalus=0;
        for(int i=0;i<listCard.size();i++){
            if(listCard.get(i).getNbReturn()>0){
                ptMalus+=listCard.get(i).getNbReturn()-1;
            }
        }
        int nbStar;
        if(ptMalus<=2)
            nbStar=3;
        else if(ptMalus<=5)
            nbStar=2;
        else
            nbStar=1;

        new Handler().postDelayed(() -> {
        Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
        intent.putExtra("starsNumber", nbStar);
        startActivity(intent);
        finish();
        }, 2000);

        return true;
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
        db = CreateDatabase.getInstance(Memory.this);

        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        String themeName = settings.getString("themeName", "");
        if(themeName.equals("Chiffres") ){
            initCardChiffre(9);
        }

        shuffle();


        GridView gridView = findViewById(R.id.gridview_memory);

        calculatesNbColumns();
        gridView.setNumColumns(numColumns);

        MemoryAdapter memoryAdapter = new MemoryAdapter(getApplicationContext(), listCard,numColumns);
        gridView.setAdapter(memoryAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(!isClicked) {
                    returnCard(position, memoryAdapter);

                    isWin();
                }
            }
        });
        /*recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MemoryAdapter(getApplicationContext(), listCard));*/
    }

    private void calculatesNbColumns(){
        numColumns = (int) Math.sqrt(listCard.size());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        double cardWidth = (1094.0 + 20) / numColumns ;
        double cardHeight = cardWidth* (1684.0 / 1094) + 20;

        double screenHeight = metrics.heightPixels;
        int nbRows = (int) ((listCard.size()+ numColumns - 1) / numColumns);
        if(cardHeight*nbRows>screenHeight){
            numColumns++;
        }
    }

    private void initCardChiffre(int nbCard){
        ArrayList<Integer> listChiffre = new ArrayList<>();
        listChiffre.add(R.drawable.one);
        listChiffre.add(R.drawable.two);
        listChiffre.add(R.drawable.three);
        listChiffre.add(R.drawable.four);
        listChiffre.add(R.drawable.five);
        listChiffre.add(R.drawable.six);
        listChiffre.add(R.drawable.seven);
        listChiffre.add(R.drawable.eight);
        listChiffre.add(R.drawable.nine);

        listCard = new ArrayList<>();
        if(nbCard>9){nbCard=9;}
        int value,nbChoice=0;
        boolean isUse=false;
        while(nbChoice!=nbCard){

            value =(int) (Math.random()*9);
            for (int j=0;j<listCard.size();j++){
                if(String.valueOf(value+1)==listCard.get(j).getValue()){
                    isUse=true;
                    break;
                }
            }
            if(!isUse) {
                nbChoice++;
                System.out.println("------- "+String.valueOf(value+1)+" -------");
                listCard.add(new Card(String.valueOf(value + 1), listChiffre.get(value)));
            }
            isUse=false;
        }

        int size = listCard.size();
        int sizeImage = db.appDao().getNbWords();
        for(int i=0;i<size;i++){
            this.listCard.add( new Card(listCard.get(i).getValue(),db.appDao().getWordById((int)(Math.random()*sizeImage)).getImage()));
        }
    }
}
