
package fr.dut.ptut2021.game;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.MemoryAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.MemoryCard;
import fr.dut.ptut2021.models.MemoryCardChiffre;
import fr.dut.ptut2021.models.MemoryCardLettre;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.MemoryData;
import fr.dut.ptut2021.models.database.game.MemoryDataCardCrossRef;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyTextToSpeech;
import fr.dut.ptut2021.utils.MyVibrator;


public class Memory extends AppCompatActivity implements OnStateItemClickListener {
    private ArrayList<MemoryCard> listMemoryCard;
    private int idLastCardReturn=-1;
    int numColumns;
    boolean isClicked=false;
    private CreateDatabase db;
    private int difficulty;
    private int difficultyMax;
    private int userId;
    private String category;
    private int subCat;
    private StateProgressBar stateProgressBar;
    private StateProgressBar stateProgressBarLock;
    private boolean isWin=false, haveWin = false;
    private ArrayList<Integer> fonts;

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
                        if (idCard != idLastCardReturn && listMemoryCard.get(idLastCardReturn).getValue().toUpperCase(Locale.ROOT).equals(listMemoryCard.get(idCard).getValue().toUpperCase(Locale.ROOT)) ) {
                            MyMediaPlayer.playSound(this,R.raw.correct_answer);
                            idLastCardReturn = -1;
                        } else {
                            listMemoryCard.get(idCard).setHidden(true);
                            listMemoryCard.get(idLastCardReturn).setHidden(true);
                            MyMediaPlayer.playSound(this,R.raw.wrong_answer);
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
        if(!isWin) {
            isWin = true;
            int ptMalus = 0;
            for (int i = 0; i < listMemoryCard.size(); i++) {
                if (listMemoryCard.get(i).getNbReturn() > 0) {
                    ptMalus += listMemoryCard.get(i).getNbReturn() - 1;
                }
            }
            MemoryData memoData = db.gameDao().getMemoryData(userId, category, subCat);
            int nbStar;
            if (ptMalus <= 2) {
                nbStar = 3;
                memoData.setWinStreak(db.gameDao().getMemoryData(userId, category, subCat).getWinStreak() + 1);
                memoData.setLoseStreak(0);
            } else if (ptMalus <= 5) {
                nbStar = 2;
                memoData.setWinStreak(0);
                memoData.setLoseStreak(0);
            } else {
                nbStar = 1;
                memoData.setWinStreak(0);
                memoData.setLoseStreak(db.gameDao().getMemoryData(userId, category, subCat).getLoseStreak() + 1);
            }
            if (difficulty == difficultyMax) {
                db.gameDao().updateMemoryData(memoData);
                Log.e("memory", "WinStreak : " + db.gameDao().getMemoryData(userId, category, subCat).getWinStreak());
                Log.e("memory", "LoseStreak : " + db.gameDao().getMemoryData(userId, category, subCat).getLoseStreak());
            }
            changeDifficulty();

        addGameLog(nbStar);

            new Handler().postDelayed(() -> {
                GlobalUtils.startResultPage(Memory.this, nbStar);
            }, 2000);

            return true;
        }
        return false;
    }

    private void initDB(){
        db = CreateDatabase.getInstance(Memory.this);
        String subGame = MySharedPreferences.getSubGameName(this);
        switch(subGame){
            case "Niveau 1":
                subCat=1;
                break;
            case "Niveau 2":
                subCat=2;
                break;
            case "Niveau 3":
                subCat=3;
                break;
            case "Niveau 4":
                subCat=4;
                break;
        }
        Log.e("memoryLettre","SUBGAME : "+subCat);
        db.gameDao().insertMemoryData(new MemoryData(userId,category,subCat));
        difficulty = db.gameDao().getMemoryData(userId,category,subCat).getDifficulty();
        difficultyMax = db.gameDao().getMemoryData(userId,category,subCat).getMaxDifficulty();
        Log.e("memory","BD initialisé");
        if(category.equals("Chiffres")) {
            for (int i = 0; i < 9; i++) {
                db.gameDao().insertMemoryDataCard(new MemoryDataCardCrossRef(String.valueOf(i + 1), userId, category, subCat));
            }
        }else if(category.equals("Lettres")){
            String[] alphabet = getResources().getStringArray(R.array.alphabet);
            for (int i=0;i<alphabet.length;i++){
                db.gameDao().insertMemoryDataCard(new MemoryDataCardCrossRef(alphabet[i],userId,category,subCat));
            }
        }
    }

    private void initProgressBar(){
        stateProgressBar = findViewById(R.id.progressBarMemory);
        stateProgressBarLock = findViewById(R.id.progressBarMemoryLock);
        stateProgressBar.setCurrentStateNumber(getStateNumberDifficulty(difficulty));
        stateProgressBarLock.setCurrentStateNumber(getStateNumberDifficulty(/*difficultyMax*/5));
        stateProgressBar.setOnStateItemClickListener(this);
    }

    private void initFonts(){
        fonts = new ArrayList<>();
        fonts.add(R.font.kg_second_chances);
        fonts.add(R.font.sticky_notes);
        fonts.add(R.font.arial);
        fonts.add(R.font.cocogoose);
        fonts.add(R.font.roboto);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Log.e("memory","debut Création du memory");


        category = MySharedPreferences.getThemeName(this);
        userId = MySharedPreferences.getUserId(this);
        initDB();
        initFonts();
        initProgressBar();
        initCard(getNbCard());


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
                    //v.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttoncolor));
                    returnCard(position, memoryAdapter);

                    isWin();
                }
            }
        });

    }

    private void calculatesNbColumns(){
        numColumns = (int) Math.sqrt(listMemoryCard.size());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        double cardWidth = metrics.widthPixels / numColumns ;
        double cardHeight = cardWidth* (1684.0 / 1094) + 20;

        double screenHeight = metrics.heightPixels-377.8;
        int nbRows = (int) Math.ceil(listMemoryCard.size()/(double) numColumns) ;
        while(cardHeight*nbRows>=screenHeight){
            Log.e("memoryCalcul",cardHeight+"*"+nbRows+">="+screenHeight);
            numColumns++;
            nbRows = (int) Math.ceil(listMemoryCard.size()/(double) numColumns) ;
            cardWidth = metrics.widthPixels / numColumns ;
            cardHeight = cardWidth* (1684.0 / 1094) + 20;
        }
    }

    private void initCard(int nbCard){
        listMemoryCard = new ArrayList<>();
        if(nbCard>9){nbCard=9;}
        int value,nbChoice=0;
        boolean isUsed=false;
        int nombreValeur=0;
        if(category.equals("Chiffres")){
            nombreValeur=9;
        } else if(category.equals("Lettres")){
            nombreValeur=26;
        }

        while(nbChoice!=nbCard){
            value =(int) (Math.random()*nombreValeur);
            for (int j = 0; j< listMemoryCard.size(); j++){
                    Log.e("memoryB","Le max est "+db.gameDao().getMemoryDataCardMaxUsed(userId,category,subCat));
                    if(getValue(value)==listMemoryCard.get(j).getValue()) {
                        isUsed = true;
                        break;
                    }
            }
            if(nbChoice<db.gameDao().getMemoryDataCardNbNotMaxUsed(userId,category,subCat,db.gameDao().getMemoryDataCardMaxUsed(userId,category,subCat)) && db.gameDao().getMemoryDataCard(userId, category, subCat, getValue(value)).getUsed() == db.gameDao().getMemoryDataCardMaxUsed(userId, category, subCat)) {
                isUsed=true;
            }
            if(!isUsed) {
                nbChoice++;
                if(category.equals("Chiffres")) {
                    listMemoryCard.add(new MemoryCardChiffre(getValue(value), getImage1(value)));
                }else if(category.equals("Lettres")){
                    listMemoryCard.add(new MemoryCardLettre(getValue(value), getFont1()));
                }
                if(difficulty==difficultyMax) {
                    db.gameDao().updateMemoryDataCardUsed(userId,
                            category,
                            subCat,
                            getValue(value),
                            db.gameDao().getMemoryDataCardUsed(userId,
                                    category,
                                    subCat,
                                    getValue(value)) + 1);
                }

                Log.e("memory","Carte ajouté: "+getValue(value));
            }
            isUsed=false;
        }


        Log.e("memory","Les valeurs sont choisis. La taille de la liste est de "+listMemoryCard.size());
        int size= listMemoryCard.size();
        for(int i=0;i<size;i++){
            if(category.equals("Chiffres")) {
                listMemoryCard.add( new MemoryCardChiffre(listMemoryCard.get(i).getValue(),getImage2(listMemoryCard.get(i).getDrawableImage(),Integer.parseInt(listMemoryCard.get(i).getValue()))));
            }else if(category.equals("Lettres")){
                listMemoryCard.add(new MemoryCardLettre(TolowerRelationToDifficulty(listMemoryCard.get(i).getValue()), getFont2(listMemoryCard.get(i).getFont())));
            }


            Log.e("memory","Création du double de la carte "+getValue(i));
        }

        Log.e("memory","Jeu de carte initialisé : "+listMemoryCard);
    }

    private String getValue(int value){
        if(category.equals("Chiffres")){
            return String.valueOf(value+1);
        } else if(category.equals("Lettres")){
            String[] alphabet = getResources().getStringArray(R.array.alphabet);
            return alphabet[value];
        }
        return "ERREUR: category invalid";
    }

    private String TolowerRelationToDifficulty(String value){
            switch (subCat){
                case 1:
                case 2:
                    return value;
                case 3:
                case 4:
                    return value.toLowerCase(Locale.ROOT);

            }

        return "ERREUR: category invalid";
    }

    private int NbCardUsedLessThan(int valeur){
        int compteur=0;
        for (int i=0;i<db.gameDao().getMemoryDataCardNbTotal(userId,category,subCat);i++){
            Log.e("memoryB","CARTE "+getValue(i)+" à étais utilisé : "+db.gameDao().getMemoryDataCard(userId,category,subCat,getValue(i)).getUsed());
            if(db.gameDao().getMemoryDataCard(userId,category,subCat,getValue(i)).getUsed()<valeur){
                compteur++;

            }
        }
        return compteur;
    }


    private void changeDifficulty(){
        if(difficulty==difficultyMax) {
            Log.e("memory","La difficulté est analysé");
            Log.e("memory","Nombre de carte en dessous de 3 : "+NbCardUsedLessThan(3));
            int value=3;
            if(category.equals("Lettres")){
                value=2;
            }
            if (db.gameDao().getMemoryData(userId, category, subCat).getWinStreak() >= 3 && NbCardUsedLessThan(value) == 0 && difficulty + 1 <= 5) {
                Log.e("memory", "Monte au niveau " + (difficulty + 1));
                db.gameDao().increaseMemoryDataDifficulty(userId, category, subCat);
                db.gameDao().increaseMemoryDataMaxDifficulty(userId,category,subCat);
                db.gameDao().resetAllMemoryDataStreak(userId, category, subCat);
                db.gameDao().resetAllMemoryDataCardUsed(userId, category, subCat);
            }
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


    private StateProgressBar.StateNumber getStateNumberDifficulty(int difficulty){
        switch (difficulty){
            case 1:
                return StateProgressBar.StateNumber.ONE;
            case 2:
                return StateProgressBar.StateNumber.TWO;
            case 3:
                return StateProgressBar.StateNumber.THREE;
            case 4:
                return StateProgressBar.StateNumber.FOUR;
            case 5:
                return StateProgressBar.StateNumber.FIVE;
        }
        return null;
    }

    private int getFont1(){
        return getFontLettreNotUse();
    }

    private int getFont2(int font1){
        switch(subCat) {
            case 1:
            case 3:
                return font1;
            case 2:
            case 4:
                return getFontLettreNotUse();
        }
        return 0;
    }

    private int getFontLettreNotUse(){
        int sizeFonts = fonts.size();

        if(listMemoryCard!=null && !listMemoryCard.isEmpty() ) {
            int font = listMemoryCard.get(0).getFont();
            while (!isFontNotUse(font)) {
                font = fonts.get((int) (Math.random() * sizeFonts));
            }
            return font;
        }
        else{
            return fonts.get((int) (Math.random() * sizeFonts));
        }
    }

    private boolean isFontNotUse(int font){
        for (int i=0;i<listMemoryCard.size();i++){
            if(font == listMemoryCard.get(i).getFont()){
                return false;
            }
        }
        return true;
    }


    private int getImage1(int value){
        switch(subCat){
            case 1:
            case 2:
            case 4:
                return getImageChiffreNotUse();
            case 3:
                return db.gameDao().getCard(String.valueOf(value)).getDrawableImage();
        }
        return 0;
    }
    private int getImage2(int image1,int value){
        switch(subCat){
            case 1:
            case 3:
                return image1;
            case 2:
                return getImageChiffreNotUse();
            case 4:
                return db.gameDao().getCard(String.valueOf(value)).getDrawableImage();
        }
        return 0;
    }

    private int getImageChiffreNotUse(){
        int sizeImage = db.appDao().getNbWords();
        ArrayList<Word> words = (ArrayList<Word>) db.appDao().getAllWords();
        if(listMemoryCard!=null && !listMemoryCard.isEmpty() ) {
            int img = listMemoryCard.get(0).getDrawableImage();
            while (!isImageNotUse(img)) {
                img = words.get((int) (Math.random() * sizeImage)).getImage();
            }
            return img;
        }
        else{
            return words.get((int) (Math.random() * sizeImage)).getImage();
        }
    }

    private boolean isImageNotUse(int img){
        for (int i=0;i<listMemoryCard.size();i++){
            if(img == listMemoryCard.get(i).getDrawableImage()){
                return false;
            }
        }
        return true;
    }

    private void addGameLog(int stars) {
        GameLog gameLog = new GameLog(MySharedPreferences.getGameId(this), -1, userId, stars, difficulty);
        db.gameLogDao().insertGameLog(gameLog);
    }

//TODO faire les bouton sur la progresse Bar avec max diffulté déjà atteinte
    @Override
    public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
        if(stateProgressBar == this.stateProgressBar){
            if(stateNumber<=5){
              MemoryData memoData =   db.gameDao().getMemoryData(userId,category,subCat);
              memoData.setDifficulty(stateNumber);
              db.gameDao().updateMemoryData(memoData);
              db.gameDao().resetAllMemoryDataStreak(userId, category, subCat);
              db.gameDao().resetAllMemoryDataCardUsed(userId, category, subCat);
              GlobalUtils.startGame(this,"SubMemory",true,true);
            }else{
                MyVibrator.vibrate(this, 60);
                GlobalUtils.toast(this,"Fini la difficulté "+(stateNumber-1)+" avant de pouvoir jouer à cette difficulté ",false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyTextToSpeech.stop(Memory.this);
    }

    @Override
    public void onBackPressed() {
        if(!haveWin)
            super.onBackPressed();
    }
}
