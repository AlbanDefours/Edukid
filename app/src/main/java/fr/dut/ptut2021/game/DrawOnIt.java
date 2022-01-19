package fr.dut.ptut2021.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.DataSymbol;
import fr.dut.ptut2021.models.Point;
import fr.dut.ptut2021.models.Symbol;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private CreateDatabase db;
    private ImageView image, imageVide;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private DisplayMetrics dm;
    private float largeur = 0, hauteur = 0, downx = 0, downy = 0, upx = 0, upy = 0, oldUpx = 0, oldUpy = 0;
    Boolean canDraw = false, hasDraw = false, warning = false, error = false;
    private int nbEssai = 3, nbGame = 3;
    private float nbErreur = 0;

    float tolerance, toleranceLarge;

    private Symbol s;

    private float[] getFloats(){

        int x = 0;
        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0; i < dm.widthPixels; i++){
            for(int j = 0; j < dm.heightPixels; j++){
                if(s.isInSymbol(new Point(i, j))) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);
        
        dm = getResources().getDisplayMetrics();

        db = CreateDatabase.getInstance(DrawOnIt.this);

        DataSymbol.init(dm.widthPixels, dm.heightPixels);

        tolerance = dm.widthPixels/15;
        s = new Symbol(DataSymbol.cinq, tolerance);

        toleranceLarge = tolerance * 2;

        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        //db.gameDao().get

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

        //canvas.drawPoints(getFloats(), paint);

        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((float) s.getFirstPoint().getX(),(float) s.getFirstPoint().getY(), s.getTolerance(), paint);

        paint.setColor(Color.RED);
        canvas.drawCircle((float) s.getLastPoint().getX(),(float) s.getLastPoint().getY(), s.getTolerance(), paint);

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(0.15f*largeur);
        paint.setStyle(Paint.Style.FILL);

        System.out.println("c'est bon");

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
                //System.out.println("cinq.add(new Point(" + downx/dm.widthPixels + ", " + downy/dm.heightPixels + "));");
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
                if(s.getDistanceBetweenTwoPoints(s.getLastPoint(),new Point(event.getX(), event.getY())) > s.getTolerance() && !hasDraw && canDraw){
                    canDraw = true;
                    nbEssai--;
                    nbErreur++;

                    reDraw();

                }else{
                    if(error){
                        nbEssai--;
                        nbErreur++;

                        reDraw();

                    }else if(warning){
                        nbEssai--;
                        nbErreur += 0.5;
                    }else{
                        canDraw = false;
                        hasDraw = true;
                        nbEssai = 0;
                    }

                }
                error = false;
                warning = false;

                //Toast.makeText(getApplicationContext(), "Essai : " + nbEssai + " | Erreur : " + nbErreur, Toast.LENGTH_LONG).show();

                if(nbEssai <= 0){ //Manche terminé
                    Toast.makeText(getApplicationContext(), "Symbol suivant", Toast.LENGTH_LONG).show();
                    nbEssai = 3;
                    nbGame--;
                    canDraw = true;
                    hasDraw = false;
                    reDraw();
                }
                if(nbGame <= 0){ //Partie terminé
                    //TODO animation de fni avec les etoiles et toute cette merde
                }
                oldUpx = 0;
                oldUpy = 0;
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
    }
}
