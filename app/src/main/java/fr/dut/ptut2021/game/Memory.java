package fr.dut.ptut2021.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.adapters.MemoryAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Card;
import fr.dut.ptut2021.models.database.game.MemoryData;

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
    private int difficulty;
    private int userId;
    private int subCat;

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
        if(ptMalus<=2) {
            nbStar = 3;
            db.gameDao().getMemoryData(userId).setWinStreak(db.gameDao().getMemoryData(userId).getWinStreak()+1);
            db.gameDao().getMemoryData(userId).setLoseStreak(0);
        }
        else if(ptMalus<=5) {
            nbStar = 2;
            db.gameDao().resetAllMemoryDataStreak(userId);
        }
        else{
            nbStar=1;
            db.gameDao().getMemoryData(userId).setLoseStreak(db.gameDao().getMemoryData(userId).getLoseStreak()+1);
            db.gameDao().getMemoryData(userId).setWinStreak(0);
        }

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

    private void initDB(){
        db = CreateDatabase.getInstance(Memory.this);
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        String subGame = settings.getString("subGameName", "");
        switch(subGame){
            case "Image / Image":
                subCat=1;
                break;
            case "Image / Image différente":
                subCat=2;
                break;
            case "Chiffre / Chiffre":
                subCat=3;
                break;
            case "Image / Chiffre":
                subCat=4;
                break;
        }
        db.gameDao().insertMemoryData(new MemoryData(userId,"Chiffres",subCat));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDB();

        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        String themeName = settings.getString("themeName", "");
        userId = settings.getInt("userId", 0);
        difficulty = db.gameDao().getMemoryData(userId,themeName,subCat).getDifficulty();

        if(themeName.equals("Chiffres") ){
            initCardChiffre(getNbCard());
        }
        else{

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
        System.out.println("Nombre de cartes : "+nbCard);
        listCard = new ArrayList<>();
        if(nbCard>9){nbCard=9;}
        int value,nbChoice=0;
        boolean isUsed=false;

        while(nbChoice!=nbCard){
            value =(int) (Math.random()*9)+1;
            for (int j=0;j<listCard.size();j++){
                if(nbChoice<db.gameDao().getMemoryDataCardNbNotUsed(userId,subCat)) {
                    if(String.valueOf(value)==listCard.get(j).getValue() || db.gameDao().getMemoryDataCardUsed(userId,subCat,String.valueOf(value)) == db.gameDao().getMemoryDataCardMaxUsed(userId,subCat)) {
                        isUsed = true;
                        break;
                    }
                }else{
                    if(String.valueOf(value)==listCard.get(j).getValue()) {
                        isUsed = true;
                        break;
                    }
                }
            }
            if(!isUsed) {
                nbChoice++;
                System.out.println("------- "+String.valueOf(value)+" -------");
                listCard.add(new Card(String.valueOf(value),db.gameDao().getCard(String.valueOf(value)).getDrawableImage()));
                db.gameDao().updateMemoryDataCardUsed(userId,String.valueOf(value),true);
            }
            isUsed=false;
        }

        int size = listCard.size();
        int sizeImage = db.appDao().getNbWords();
        if(){
            for(int i=0;i<size;i++){
                this.listCard.add( new Card(listCard.get(i)));
            }
        }else {
            for (int i = 0; i < size; i++) {
                this.listCard.add(new Card(listCard.get(i).getValue(), db.appDao().getWordById((int) (Math.random() * sizeImage)).getImage()));
            }
        }
    }

    private void changeDifficulty(){
        if( db.gameDao().getMemoryData(userId).getWinStreak() >= 1 && db.gameDao().getMemoryDataCardNbNotUsed(userId)==0 && difficulty+1<=16){
            System.out.println("Monte au niveau "+(difficulty+1));
            db.gameDao().increaseMemoryDataDifficultyChiffres(userId);
            db.gameDao().resetAllMemoryDataStreak(userId);
            db.gameDao().resetAllMemoryDataCardUsed(userId);
        }
        if(db.gameDao().getMemoryData(userId).getLoseStreak()>=1 && difficulty-1>=1){
            System.out.println("Baisse au niveau "+(difficulty-1));
            db.gameDao().decreaseMemoryDataDifficultyChiffres(userId);
            db.gameDao().resetAllMemoryDataStreak(userId);
            db.gameDao().resetAllMemoryDataCardUsed(userId);
        }
    }

    private int getNbCard(){
        if(difficulty==1 )
            return 2;
        if(difficulty==2 )
            return 3;
        if(difficulty==3 )
            return 4;
        if(difficulty==4 )
            return 5;
        if(difficulty==5 )
            return 6;
        return 1;
    }

    private int getImage1(int value){
        int sizeImage = db.appDao().getNbWords();
        switch(subCat){
            case 1:
            case 2:
            case 4:
                return db.appDao().getWordById((int) (Math.random() * sizeImage)).getImage();
            case 3:
                return db.gameDao().getCard(String.valueOf(value)).getDrawableImage();
        }
        return 0;
    }
    private int getImage2(int image1,int value){
        int sizeImage = db.appDao().getNbWords();
        switch(subCat){
            case 1:
            case 3:
                return image1;
            case 2:
                int img=0;
                while(img==image1){
                    db.appDao().getWordById((int) (Math.random() * sizeImage)).getImage();
                }
                return img;
            case 4:
                return db.gameDao().getCard(String.valueOf(value)).getDrawableImage();
        }
    }

}
