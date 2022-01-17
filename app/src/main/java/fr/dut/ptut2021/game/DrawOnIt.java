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

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.DataSymbol;
import fr.dut.ptut2021.models.Point;
import fr.dut.ptut2021.models.Symbol;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private ImageView image, imageVide;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private DisplayMetrics dm;
    private float largeur = 0, hauteur = 0, downx = 0, downy = 0, upx = 0, upy = 0;

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

        DataSymbol.init(dm.widthPixels, dm.heightPixels);

        s = new Symbol(DataSymbol.cinq, 40); // dm.widthPixels/10


        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float largeur = currentDisplay.getWidth();
        float hauteur = currentDisplay.getHeight();

        bitmap = Bitmap.createBitmap((int) largeur, (int) hauteur, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.RED); //YELLOW
        paint.setStrokeWidth(0.02f*largeur);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        imageVide.setImageBitmap(bitmap);

        //canvas.drawLine(0.25f*dw, 0.25f*dh, 0.5f*dw, 0.5f*dh, paint);

        //canvas.drawPoints(getFloats(), paint);

        canvas.drawPoint(400, 900, paint);
        canvas.drawPoint(600, 1100, paint);

        System.out.println("//////////////");
        System.out.println(s.isInArea2(new Point(500,1000), new Point(400, 900), new Point(600, 1100)));
        System.out.println("//////////////");

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
                //System.out.println("cinq.add(new Point(" + downx/dm.widthPixels + ", " + downy/dm.heightPixels + "));");
                break;
            case MotionEvent.ACTION_MOVE:
                //System.out.println("la");
                upx = event.getX();
                upy = event.getY();
                if(s.isInSymbol(new Point(upx, upy))) {
                    canvas.drawPoint(upx, upy, paint);
                }
                imageVide.invalidate();
                //System.out.println("Coordonnes : " + upx + " " + upy);
                //System.out.println("             " + dm.widthPixels + " " + dm.heightPixels);
                if(!s.isInSymbol(new Point(upx, upy))){
                    //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY); //ligne de william, met tous le canvas en transparent
                    //canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR); // ou elle
                }
                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    //Permet afficher point du clic
    /*@Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                canvas.drawPoint(downx, downy, paint);
                imageVide.invalidate();
                System.out.println(downx/largeur + " " + downy/hauteur);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }*/
}
