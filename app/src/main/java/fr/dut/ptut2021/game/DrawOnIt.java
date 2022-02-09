package fr.dut.ptut2021.game;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.utils.FSize;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.DataSymbol;
import fr.dut.ptut2021.models.Point;
import fr.dut.ptut2021.models.Symbol;
import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MyTextToSpeech;
import fr.dut.ptut2021.models.database.game.DrawOnItData;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyVibrator;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private CreateDatabase db;
    private ImageView image, imageVide;
    private Bitmap bitmap, bitmapFleche;
    private Canvas canvas;
    private Paint paint;
    private DisplayMetrics dm;
    private float largeur = 0, hauteur = 0, downx = 0, downy = 0, upx = 0, upy = 0, oldUpx = 0, oldUpy = 0;
    private Boolean canDraw = false, hasDraw = false, warning = false, error = false, next = false, isStart = false;
    private Boolean haveWin = false;


    private static final int NBESSAI = 3, NBGAME = 4;

    private int numEssai = 0, numGame = 0, numTrait = 0;
    private float nbErreur = 0;
    private Card[] carte;

    private android.graphics.Point p;

    private int userId;
    private String themeName;

    private float tolerance, toleranceLarge;

    private Symbol s;

    private float[] getFloats(){

        int x = 0;
        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0; i < dm.widthPixels; i++){
            for(int j = 0; j < dm.heightPixels; j++){
                if(s.isInSymbol(new Point(i, j), toleranceLarge)){
                    points.add(new Point(i, j));
                    System.out.println("x : " + i + "  y : " + j);
                }
            }
        }

        float[] pointsF = new float[points.size() * 2];

        for(int i = 0; i < points.size(); i++){
            pointsF[i * 2] = (float) points.get(i).getX();
            pointsF[i * 2 + 1] = (float) points.get(i).getY();
        }
        return pointsF;
    }

    private float[] getPointsToDraw(){
        System.out.println("here");
        float[] tab = new float[s.getPoints().size() * 2];
        for(int i = 0; i < s.getPoints().size() * 2; i+=2){
            tab[i] = (float) s.getPoints().get(i/2).getX();
            tab[i + 1] = (float) s.getPoints().get(i/2).getY();
        }
        return tab;
    }

    private void initDatabase() {
        db = CreateDatabase.getInstance(DrawOnIt.this);

        String[] alphabetTab = getResources().getStringArray(R.array.alphabet);
        for (String letter : alphabetTab)
            db.gameDao().insertDOIData(new DrawOnItData(userId, letter, "Lettres", 1));

        for (int i = 1; i < 10; i++)
            db.gameDao().insertDOIData(new DrawOnItData(userId, Integer.toString(i), "Chiffres", 1));
    }

    private void getSharedPref() {
        userId = MySharedPreferences.getUserId(this);
        themeName = MySharedPreferences.getThemeName(this);
    }

    private void updateGameData() {
        DrawOnItData data = db.gameDao().getDOIData(userId, carte[numGame].getCardValue());
        data.setLastUsed(1);

        if (numEssai == 0) {
            data.setWin(data.getWin() + 1);
            data.setWinStreak(data.getWinStreak() + 1);
            data.setLoseStreak(0);
        } else {
            data.setLose(data.getLose() + 1);
            data.setLoseStreak(data.getLoseStreak() + 1);
            data.setWinStreak(0);
        }
        db.gameDao().updateDOIData(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);
        
        dm = getResources().getDisplayMetrics();

        tolerance = dm.widthPixels/15;

        toleranceLarge = tolerance * 2;

        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        carte = new Card[NBGAME];

        db = CreateDatabase.getInstance(DrawOnIt.this);

        getSharedPref();
        initDatabase();

        int[] numRand = new int[NBGAME];

        List<Card> listCard = db.gameDao().getAllCardByType("Chiffres");

        for(int i = 0; i < NBGAME; i++){
            numRand[i] = (int) (Math.random() * listCard.size());
            for(int j = 0; j < i; j++){
                if(numRand[i] == numRand[j]){
                    j = i;
                    i--;
                }
            }
        }

        for(int i = 0; i < NBGAME; i++){
            carte[i] = listCard.get(numRand[i]);
        }


        Display currentDisplay = getWindowManager().getDefaultDisplay();
        largeur = currentDisplay.getWidth();
        hauteur = currentDisplay.getHeight();

        p = new android.graphics.Point();
        currentDisplay.getRealSize(p);

        /*
        int c = 4;
        image.setImageResource(listCard.get(c - 1).getDrawableImage()); //carte[0]
        DataSymbol.initPts(c, dm.xdpi, dm.ydpi); //Integer.parseInt(carte[0].getCardValue())
        s = new Symbol(DataSymbol.getPts(), tolerance);
        */

        image.setImageResource(carte[0].getDrawableImage()); //carte[0]
        DataSymbol.initPts(Integer.parseInt(carte[0].getCardValue()),largeur, hauteur); //(Integer.parseInt(carte[0].getCardValue()), dm.widthPixels, dm.heightPixels)
        s = new Symbol(DataSymbol.getPts(), tolerance);


        //Log.e("bit", "" + bitmap.getScaledHeight(canvas));
        //Log.e("bit", "" + bitmap.getScaledHeight(dm));

        //System.out.println(bitmap.getScaledHeight(canvas));
        //System.out.println(bitmap.getScaledHeight(dm));



        bitmap = Bitmap.createBitmap((int) largeur, (int) hauteur, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(0.15f*largeur);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        imageVide.setImageBitmap(bitmap);

        // ---------------------debug---------------------
        //paint.setStrokeWidth(15);
        //canvas.drawPoints(getFloats(), paint); // dessine tout les point compris dans la zone qui est calculé par l'algo
        //canvas.drawPoints(getPointsToDraw(), paint); // dessine les points enregistré pour le symbole selectionner

        /*
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) s.getFirstPoint().getX(),(float) s.getFirstPoint().getY(), s.getTolerance(), paint);

        paint.setColor(Color.RED);
        canvas.drawCircle((float) s.getLastPoint().getX(),(float) s.getLastPoint().getY(), s.getTolerance(), paint);

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(0.15f*largeur);
        paint.setStyle(Paint.Style.FILL);
        */

        bitmapFleche = BitmapFactory.decodeResource(getResources(), R.drawable.fleche);
        bitmapFleche = Bitmap.createScaledBitmap(bitmapFleche, 100, 70, false);

        reDraw();

        System.out.println("c'est bon");
        Log.e("axel", "c'est bon");

        Log.e("dim", "w : " + p.x + " h : " + p.y);

        imageVide.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e("debug", "ACTION_DOWN");
                downx = event.getX();
                downy = event.getY();
                Log.e("var", "isStart : " + isStart);
                Log.e("var", "numTrait : " + numTrait);
                if(s.getDistanceBetweenTwoPoints(s.getFirstPoint(),new Point(downx, downy)) <= s.getTolerance() && !hasDraw && !isStart){
                    canDraw = true;
                    Log.e("list", numTrait + "");
                }else if(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size() - 2 >= numTrait - 1 && numTrait > 0) {
                    if (s.getPoints().size() - 1 >= DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait - 1) + 1) {
                        if (s.getDistanceBetweenTwoPoints(s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait - 1) + 1), new Point(downx, downy)) <= s.getTolerance() && !hasDraw) {
                            canDraw = true;
                        } else {
                            canDraw = false;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("debug", "ACTION_MOVE");
                upx = event.getX();
                upy = event.getY();
                Log.e("var", "canDraw : " + canDraw);
                Log.e("var", "hasDraw : " + hasDraw);
                if(canDraw && !hasDraw) {
                    if(oldUpx != 0 && oldUpy != 0){
                        paint.setStrokeWidth(0.15f*largeur);
                        canvas.drawLine(upx, upy, oldUpx, oldUpy, paint);
                    }else{
                        canvas.drawPoint(upx, upy, paint);
                    }
                    Log.e("dernier", "" + s.getLastId() + "  " + s.getPoints().size());
                    oldUpx = upx;
                    oldUpy = upy;
                }
                imageVide.invalidate();
                if(!s.isInSymbol(new Point(upx, upy))){
                    if(!s.isInSymbol(new Point(upx, upy), toleranceLarge)){
                        paint.setColor(Color.RED);
                        error = true;
                    }else {
                        paint.setColor(Color.argb(255,255,200,0));
                        warning = true;
                    }
                }else{
                    paint.setColor(Color.GREEN);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("debug", "ACTION_UP");
                if(canDraw) {

                    //if (s.getDistanceBetweenTwoPoints(s.getLastPoint(), new Point(event.getX(), event.getY())) > s.getTolerance() || s.getLastId() != s.getPoints().size() - 1 || !s.isPastByTheMiddle()) {

                    /*
                    System.out.println("----------------------------------------");
                    System.out.println(Integer.parseInt(carte[numGame].getCardValue()));
                    System.out.println(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait));
                    System.out.println(s.getDistanceBetweenTwoPoints(s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait)), new Point(event.getX(), event.getY())) > s.getTolerance());
                    System.out.println("----------------------------------------");
                    */

                    if (s.getDistanceBetweenTwoPoints(s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait)), new Point(event.getX(), event.getY())) > s.getTolerance() || s.getLastId() != DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(numTrait) || !s.isPastByTheMiddle()) {
                        numEssai++;
                        nbErreur++;

                        Toast.makeText(getApplicationContext(), "Passe par tous les points", Toast.LENGTH_SHORT).show();

                        Log.e("axel", "pas arriver au dernier point");

                        Log.e("list", "Erreur multiple trait");

                        Log.e("bool", "passer ici");
                    }else{
                        if (error) {
                            numEssai++;
                            nbErreur++;

                            Log.e("axel", "Beacoup depassé");
                            Toast.makeText(getApplicationContext(), "Beacoup depassé", Toast.LENGTH_SHORT);

                            //reDraw();

                        } else if (warning) {
                            nbErreur += 0.5;
                            Log.e("axel", "Un peu depassé");
                            Toast.makeText(getApplicationContext(), "Un peu depassé", Toast.LENGTH_SHORT);
                            next = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Bravo !", Toast.LENGTH_SHORT);
                            next = true;
                        }

                    }

                    Log.e("bool", "numTrait  : " + numTrait);
                    Log.e("bool", "size : " + DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size());

                    if(numTrait != DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size() - 2 && next && !isStart) {
                        if (numTrait < DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size() - 2 && next) {
                            next = false;
                            numTrait++;
                            isStart = true;
                        }else{
                            isStart = false;
                        }
                    }else{
                        isStart = false;
                    }

                    Log.e("bool", "next : " + next);

                    if (numEssai >= NBESSAI) { //Manche terminé
                        Log.e("axel", "Symbol suivant : plus d'essai | nb de game " + numGame);
                        Toast.makeText(getApplicationContext(), "Symbol suivant : plus d'essai", Toast.LENGTH_SHORT);

                        next = true;
                    }
                    if (numGame >= NBGAME - 1 && next) { //Partie terminé
                        haveWin = true;
                        hasDraw = true;
                        Toast.makeText(getApplicationContext(), "Jeu terminé !!!", Toast.LENGTH_SHORT);
                        Log.e("axel", "jeu terminé !!!");

                        Intent intent = new Intent(getApplicationContext(), ResultGamePage.class);
                        int stars;
                        if(((NBESSAI*NBGAME)/3.0)*2.0 < nbErreur){
                            stars = 1;
                        }else if(((NBESSAI*NBGAME)/3.0) > nbErreur){
                            stars = 3;
                        }else{
                            stars = 2;
                        }
                        addGameLogInDb(stars);
                        intent.putExtra("starsNumber", stars);
                        startActivity(intent);
                        finish();

                    } else if (next) {
                        updateGameData();
                        nextSymbol();
                        if(error){
                            MyMediaPlayer.playSound(this, R.raw.wrong_answer);
                        }else{
                            MyMediaPlayer.playSound(this, R.raw.correct_answer);
                        }
                        numTrait = 0;
                        next = false;
                    }else if(!isStart){
                        MyMediaPlayer.playSound(this, R.raw.wrong_answer);
                        MyVibrator.vibrate(this, 60);
                        reDraw();
                        numTrait = 0;
                        canDraw = false;
                        Log.e("bool", "canDraw : " + canDraw);
                    }
                    if(isStart){
                        canDraw = true;
                        Log.e("bool", "canDraw : " + canDraw);
                    }
                    oldUpx = 0;
                    oldUpy = 0;
                    error = false;
                    warning = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    private void addGameLogInDb(int stars) {
        GameLog gameLog = new GameLog(MySharedPreferences.getGameId(this), -1, userId, stars, db.gameDao().getDOIDataMaxDif(userId, carte[numGame].getCardValue()));
        db.gameLogDao().insertGameLog(gameLog);
    }

    public void reDraw(){

        Log.e("debug", "reDraw");

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        paint.setStrokeWidth(15);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) s.getFirstPoint().getX(),(float) s.getFirstPoint().getY(),s.getTolerance(), paint);
        for(int i = 0; i < DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size() - 2; i++){
            canvas.drawCircle((float) s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(i) + 1).getX(),(float) s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(i) + 1).getY(), s.getTolerance(), paint);
        }

        paint.setColor(Color.RED);
        canvas.drawCircle((float) s.getLastPoint().getX(),(float) s.getLastPoint().getY(), s.getTolerance(), paint);
        for(int i = 0; i < DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).size() - 1; i++){
            canvas.drawCircle((float) s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(i)).getX(),(float) s.getPoints().get(DataSymbol.getNbTrait().get(Integer.parseInt(carte[numGame].getCardValue())).get(i)).getY(), s.getTolerance(), paint);
        }
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(0.15f*largeur);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        double tailleFleche = 0;

        for(int i = 0; i < s.getPoints().size() - 1; i+=2) {
            float degres = (float) Math.toDegrees(Math.atan((s.getPoints().get(i + 1).getY()-s.getPoints().get(i).getY())/(s.getPoints().get(i + 1).getX()-s.getPoints().get(i).getX())));
            Log.e("deg", "1 " + (s.getPoints().get(i).getY() - s.getPoints().get(i+1).getY())/(s.getPoints().get(i).getX() - s.getPoints().get(i+1).getX()));
            Log.e("deg", "2 " + (s.getPoints().get(i).getX() < s.getPoints().get(i + 1).getX()));
            if(s.getPoints().get(i).getX() > s.getPoints().get(i + 1).getX()){
                degres += 180;
            }
            canvas.drawBitmap(getRotateFleche(degres),(float) s.getPoints().get(i).getX() - (float) (bitmapFleche.getWidth()/2),(float) s.getPoints().get(i).getY() - (float) (bitmapFleche.getHeight()/2), paint);
        }

        s.clearAllLastId();

    }

    public void nextSymbol(){

        Log.e("debug", "nextSymbol");

        numEssai = 0;
        numGame++;

        image.setImageResource(carte[numGame].getDrawableImage());
        DataSymbol.initPts(Integer.parseInt(carte[numGame].getCardValue()),largeur, hauteur); //dm.widthPixels, dm.heightPixels
        s = new Symbol(DataSymbol.getPts(), tolerance);
        reDraw();
    }

    private Bitmap getRotateFleche(float degre){
        Matrix matrix = new Matrix();
        matrix.postRotate(degre);

        return Bitmap.createBitmap(bitmapFleche, 0, 0, bitmapFleche.getWidth(), bitmapFleche.getHeight(), matrix, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyTextToSpeech.getInstance().stop(DrawOnIt.this);
    }

    @Override
    public void onBackPressed() {
        if(!haveWin)
            super.onBackPressed();
    }
}


/*
for(int i = 0; i < s.getPoints().size(); i+=2){
            tailleFleche = s.getDistanceBetweenTwoPoints(s.getPoints().get(i), s.getPoints().get(i+1)) * 10/100;
            paint.setColor(Color.RED);
            canvas.drawLine((float) s.getPoints().get(i).getX(), (float) s.getPoints().get(i).getY(), (float) s.getPoints().get(i + 1).getX(), (float) s.getPoints().get(i + 1).getY(), paint);
            deltaX = (float) (s.getPoints().get(i + 1).getX() - s.getPoints().get(i).getX());
            deltaY = (float) (s.getPoints().get(i + 1).getY() - s.getPoints().get(i).getY());

            m = deltaY / deltaX;
            b = (float) ((-m * s.getPoints().get(i+1).getX()) + s.getPoints().get(i+1).getY());

            x1_1 = (float)( (-2*b*m) - Math.sqrt((Math.pow(2*b*m, 2))-(4 * (1+(m*m)) * (tailleFleche*tailleFleche) - (b*b)))/(2*(1+(m*m))));
            x1_2 = (float)( (-2*b*m) + Math.sqrt((Math.pow(2*b*m, 2))-(4 * (1+(m*m)) * (tailleFleche*tailleFleche) - (b*b)))/(2*(1+(m*m))));

            p = new Point(s.getPoints().get(i+1).getX() + x1_1, ((s.getPoints().get(i+1).getX() + x1_1) * m) + b);

            m2 = -1/m;
            b2 = (float) ((-m2 * p.getX()) + p.getY());

            x2_1 = (float)( (-2*b2*m2) - Math.sqrt((Math.pow(2*b2*m2, 2))-(4 * (1+(m2*m2)) * (tailleFleche*tailleFleche) - (b2*b2)))/(2*(1+(m2*m2))));
            x2_2 = (float)( (-2*b2*m2) + Math.sqrt((Math.pow(2*b2*m2, 2))-(4 * (1+(m2*m2)) * (tailleFleche*tailleFleche) - (b2*b2)))/(2*(1+(m2*m2))));

            p2 = new Point(p.getX() + x2_1, ((p.getX() + x2_1) * m2) + b2);


            paint.setColor(Color.BLUE);
            canvas.drawPoint((float) p.getX(),(float) p.getY(), paint);
            paint.setColor(Color.RED);
            canvas.drawPoint((float) p2.getX(),(float) p2.getY(), paint);
            Log.e("point", "px " + p.getX() + ", py " + p.getY() + ", p2x " + p2.getX() + ", p2y " + p2.getY());
            canvas.drawLine((float) s.getPoints().get(i + 1).getX(), (float) s.getPoints().get(i + 1).getY(), (float) p2.getX(), (float) p2.getY(), paint);
        }*/
