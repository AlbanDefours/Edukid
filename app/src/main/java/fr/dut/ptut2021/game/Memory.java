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
import fr.dut.ptut2021.models.MemoryCard;
import fr.dut.ptut2021.models.database.game.MemoryData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Memory extends AppCompatActivity {
    private ArrayList<MemoryCard> listMemoryCard;
    private int idLastCardReturn=-1;
    int numColumns;
    boolean isClicked=false;
    private MediaPlayer mpGoodAnswer;
    private MediaPlayer mpWrongAnswer;
    private CreateDatabase db;
    private int difficulty;
    private int userId;
    private String category;
    private int subCat;

    private void shuffle(){
        Collections.shuffle(listMemoryCard);
        Collections.shuffle(listMemoryCard);
        Collections.shuffle(listMemoryCard);
        Collections.shuffle(listMemoryCard);
        Collections.shuffle(listMemoryCard);
        Collections.shuffle(listMemoryCard);
    }

    public void returnCard(int idCard,MemoryAdapter memoryAdapter) {
        if(listMemoryCard.get(idCard).isHidden()){
            ArrayList<Integer> returnableCards=new ArrayList<>();
            isClicked=true;
            listMemoryCard.get(idCard).setHidden(false);
            returnableCards.add(idCard);
            memoryAdapter.setCard(returnableCards);
            memoryAdapter.notifyDataSetChanged();


                if (idLastCardReturn == -1) {
                    idLastCardReturn = idCard;
                    isClicked=false;
                } else {
                    new Handler().postDelayed(() -> {
                        if (idCard != idLastCardReturn && listMemoryCard.get(idLastCardReturn).getValue() == listMemoryCard.get(idCard).getValue()) {
                            mpGoodAnswer.start();
                            idLastCardReturn = -1;
                        } else {
                            listMemoryCard.get(idCard).setHidden(true);
                            listMemoryCard.get(idLastCardReturn).setHidden(true);
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
        for(int i = 0; i< listMemoryCard.size(); i++){
            if(listMemoryCard.get(i).isHidden()){
                return false;
            }
        }
        int ptMalus=0;
        for(int i = 0; i< listMemoryCard.size(); i++){
            if(listMemoryCard.get(i).getNbReturn()>0){
                ptMalus+= listMemoryCard.get(i).getNbReturn()-1;
            }
        }
        int nbStar;
        if(ptMalus<=2) {
            nbStar = 3;
            db.gameDao().getMemoryData(userId,category,subCat).setWinStreak(db.gameDao().getMemoryData(userId,category,subCat).getWinStreak()+1);
            db.gameDao().getMemoryData(userId,category,subCat).setLoseStreak(0);
        }
        else if(ptMalus<=5) {
            nbStar = 2;
            db.gameDao().resetAllMemoryDataStreak(userId,category,subCat);
        }
        else{
            nbStar=1;
            db.gameDao().getMemoryData(userId,category,subCat).setLoseStreak(db.gameDao().getMemoryData(userId,category,subCat).getLoseStreak()+1);
            db.gameDao().getMemoryData(userId,category,subCat).setWinStreak(0);
        }
        changeDifficulty();

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
        for (MemoryCard memoryCard : listMemoryCard){
            if(!memoryCard.isHidden()){
                System.out.print(memoryCard.getValue()+" ");
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
        db.gameDao().insertMemoryData(new MemoryData(userId,category,subCat));
        Log.e("memory","BD initialisé");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        category = settings.getString("themeName", "");
        userId = settings.getInt("userId", 0);
        initDB();

        mpGoodAnswer = MediaPlayer.create(this, R.raw.correct_answer);
        mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        difficulty = db.gameDao().getMemoryData(userId,category,subCat).getDifficulty();

        if(category.equals("Chiffres") ){
            initCardChiffre(getNbCard());
        }
        else{

        }

        shuffle();


        GridView gridView = findViewById(R.id.gridview_memory);

        calculatesNbColumns();
        gridView.setNumColumns(numColumns);

        MemoryAdapter memoryAdapter = new MemoryAdapter(getApplicationContext(), listMemoryCard,numColumns);
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
        numColumns = (int) Math.sqrt(listMemoryCard.size());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        double cardWidth = (1094.0 + 20) / numColumns ;
        double cardHeight = cardWidth* (1684.0 / 1094) + 20;

        double screenHeight = metrics.heightPixels;
        int nbRows = (int) ((listMemoryCard.size()+ numColumns - 1) / numColumns);
        if(cardHeight*nbRows>screenHeight){
            numColumns++;
        }
    }

    private void initCardChiffre(int nbCard){
        System.out.println("Nombre de cartes : "+nbCard);
        listMemoryCard = new ArrayList<>();
        if(nbCard>9){nbCard=9;}
        int value,nbChoice=0;
        boolean isUsed=false;

        while(nbChoice!=nbCard){
            value =(int) (Math.random()*9)+1;
            for (int j = 0; j< listMemoryCard.size(); j++){
                if(nbChoice<db.gameDao().getMemoryDataCardNbNotMaxUsed(userId,category,subCat,db.gameDao().getMemoryDataCardMaxUsed(userId,category,subCat))) {
                    if(String.valueOf(value)==listMemoryCard.get(j).getValue() || db.gameDao().getMemoryDataCardUsed(userId,category,subCat,String.valueOf(value)) == db.gameDao().getMemoryDataCardMaxUsed(userId,category,subCat)) {
                        isUsed = true;
                        break;
                    }
                }else{
                    if(String.valueOf(value)== listMemoryCard.get(j).getValue()) {
                        isUsed = true;
                        break;
                    }
                }
            }
            if(!isUsed) {
                nbChoice++;
                listMemoryCard.add(new MemoryCard(String.valueOf(value),getImage1(value)));
                db.gameDao().updateMemoryDataCardUsed(userId,
                        category,
                        subCat,
                        String.valueOf(value),
                        db.gameDao().getMemoryDataCardUsed(userId,
                                category,
                                subCat,
                                String.valueOf(value))+1);

                Log.e("memory","Carte ajouté: "+value);
            }
            isUsed=false;
        }

        Log.e("memory","Les valeurs sont choisis. La taille de la liste est de "+listMemoryCard.size());
        int size= listMemoryCard.size();
        for(int i=0;i<size;i++){
            this.listMemoryCard.add( new MemoryCard(listMemoryCard.get(i).getValue(),getImage2(listMemoryCard.get(i).getDrawableImage(),Integer.parseInt(listMemoryCard.get(i).getValue()))));

            Log.e("memory","Création du double de la carte "+(i+1));
        }

        Log.e("memory","Jeu de carte initialisé : "+listMemoryCard);
    }

    private void changeDifficulty(){
        Log.e("memory","La difficulté est analysé");
        if( db.gameDao().getMemoryData(userId,category,subCat).getWinStreak() >= 1 && db.gameDao().getMemoryDataCardNbTotal(userId,category,subCat)-db.gameDao().getMemoryDataCardNbUsedMoreThan(userId,category,subCat,3)==0 && difficulty+1<=5){
            Log.e("memory","Monte au niveau "+(difficulty+1));
            db.gameDao().increaseMemoryDataDifficulty(userId,category,subCat);
            db.gameDao().resetAllMemoryDataStreak(userId,category,subCat);
            db.gameDao().resetAllMemoryDataCardUsed(userId,category,subCat);
        }
        if(db.gameDao().getMemoryData(userId,category,subCat).getLoseStreak()>=1 && difficulty-1>=1){
            Log.e("memory","Baisse au niveau "+(difficulty-1));
            db.gameDao().decreaseMemoryDataDifficulty(userId,category,subCat);
            db.gameDao().resetAllMemoryDataStreak(userId,category,subCat);
            db.gameDao().resetAllMemoryDataCardUsed(userId,category,subCat);
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
                int img=image1;
                while(img==image1){
                    img = db.appDao().getWordById((int) (Math.random() * sizeImage)).getImage();
                }
                Log.e("memory","Image 2 = "+img);
                return img;
            case 4:
                return db.gameDao().getCard(String.valueOf(value)).getDrawableImage();
        }
        return 0;
    }

}
