package fr.dut.ptut2021.game;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
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

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.DataSymbol;
import fr.dut.ptut2021.models.Point;
import fr.dut.ptut2021.models.Symbol;
import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.utils.MyMediaPlayer;
import fr.dut.ptut2021.utils.MyVibrator;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private CreateDatabase db;
    private ImageView image, imageVide;
    private Bitmap bitmap, bitmapFleche;
    private Canvas canvas;
    private Paint paint;
    private DisplayMetrics dm;
    private float largeur = 0, hauteur = 0, downx = 0, downy = 0, upx = 0, upy = 0, oldUpx = 0, oldUpy = 0;
    private Boolean canDraw = false, hasDraw = false, warning = false, error = false, next = false;

    private static final int NBESSAI = 3, NBGAME = 9;

    private int numEssai = 0, numGame = 0;
    private float nbErreur = 0;
    private Card[] carte;


    float tolerance, toleranceLarge;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);
        
        dm = getResources().getDisplayMetrics();

        tolerance = dm.widthPixels/15; // essaye avec 18

        toleranceLarge = tolerance * 2;

        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        carte = new Card[NBGAME];


        db = CreateDatabase.getInstance(DrawOnIt.this);

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

        int c = 8;
        image.setImageResource(carte[0].getDrawableImage()); //carte[0]
        DataSymbol.initPts(Integer.parseInt(carte[0].getCardValue()), dm.widthPixels, dm.heightPixels); //Integer.parseInt(carte[0].getCardValue())
        s = new Symbol(DataSymbol.getPts(), tolerance);


        Display currentDisplay = getWindowManager().getDefaultDisplay();
        largeur = currentDisplay.getWidth();
        hauteur = currentDisplay.getHeight();

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

        imageVide.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                if(s.getDistanceBetweenTwoPoints(s.getFirstPoint(),new Point(downx, downy)) <= s.getTolerance() && !hasDraw){
                    canDraw = true;
                }
                //Log.e("Axel","cinq.add(new Point(" + downx/dm.widthPixels + ", " + downy/dm.heightPixels + "));");
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
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
                System.out.println("ACTION_UP");
                if(canDraw) {
                    if (s.getDistanceBetweenTwoPoints(s.getLastPoint(), new Point(event.getX(), event.getY())) > s.getTolerance() || s.getLastId() != s.getPoints().size() - 1 || !s.isPastByTheMiddle()) {
                        numEssai++;
                        nbErreur++;

                        Toast.makeText(getApplicationContext(), "Passe par tous les points", Toast.LENGTH_SHORT).show();

                        Log.e("axel", "pas arriver au dernier point");

                        //reDraw();

                    } else {
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
                    error = false;
                    warning = false;

                    if (numEssai >= NBESSAI) { //Manche terminé
                        Log.e("axel", "Symbol suivant : plus d'essai | nb de game " + numGame);
                        Toast.makeText(getApplicationContext(), "Symbol suivant : plus d'essai", Toast.LENGTH_SHORT);

                        next = true;
                    }
                    if (numGame >= NBGAME - 1 && next) { //Partie terminé
                        hasDraw = true;
                        Toast.makeText(getApplicationContext(), "Jeu terminé !!!", Toast.LENGTH_SHORT);
                        Log.e("axel", "jeu terminé !!!");
                        //TODO animation de fni avec les etoiles et toute cette merde
                    } else if (next) {
                        nextSymbol();
                        //MyMediaPlayer.playSound(this, R.raw.correct_answer);
                        next = false;
                    }else{
                        //MyMediaPlayer.playSound(this, R.raw.wrong_answer);
                        //MyVibrator.vibrate(this, 60);
                        reDraw();
                    }
                    oldUpx = 0;
                    oldUpy = 0;
                    canDraw = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    public void reDraw(){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        paint.setStrokeWidth(15);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) s.getFirstPoint().getX(),(float) s.getFirstPoint().getY(),s.getTolerance(), paint);

        paint.setColor(Color.RED);
        canvas.drawCircle((float) s.getLastPoint().getX(),(float) s.getLastPoint().getY(), s.getTolerance(), paint);

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(0.15f*largeur);
        paint.setStyle(Paint.Style.FILL);

        Point p, p2;
        float deltaX, deltaY, m, b, m2, b2, x1_1, x1_2, x2_1, x2_2;
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
            canvas.drawBitmap(getRotateFleche(degres),(float) s.getPoints().get(i).getX() - bitmapFleche.getWidth()/2,(float) s.getPoints().get(i).getY() - bitmapFleche.getHeight()/2, paint);
        }

        s.clearAllLastId();

    }

    public void nextSymbol(){

        Log.e("axel", "debut nextSymbol | numGame : " + numGame);

        numEssai = 0;
        numGame++;

        image.setImageResource(carte[numGame].getDrawableImage());
        DataSymbol.initPts(Integer.parseInt(carte[numGame].getCardValue()), dm.widthPixels, dm.heightPixels);
        s = new Symbol(DataSymbol.getPts(), tolerance);

        Log.e("axel", "millieu nextSymbol");

        reDraw();


        Log.e("axel", "fin nextSymbol");
    }

    private Bitmap getRotateFleche(float degre){
        Matrix matrix = new Matrix();
        matrix.postRotate(degre);

        return Bitmap.createBitmap(bitmapFleche, 0, 0, bitmapFleche.getWidth(), bitmapFleche.getHeight(), matrix, true);
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