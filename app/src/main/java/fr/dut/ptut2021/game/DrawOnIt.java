package fr.dut.ptut2021.game;

<<<<<<< HEAD
import fr.dut.ptut2021.models.Symbol;
import fr.dut.ptut2021.models.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawOnIt {

    private Map<String, Symbol> symbols = new HashMap<>();
    ArrayList<Point> points = new ArrayList<>();

    public DrawOnIt(){
        symbols.put("0", new Symbol());

        symbols.put("1", new Symbol());

        symbols.put("2", new Symbol());

        symbols.put("3", new Symbol());

        symbols.put("4", new Symbol());

        symbols.put("5", new Symbol());

        symbols.put("6", new Symbol());

        symbols.put("7", new Symbol());

        symbols.put("8", new Symbol());

        symbols.put("9", new Symbol());

        points.add(new Point(10,10));
        points.add(new Point(30,50));
        points.add(new Point(50,90));
        points.add(new Point(70,130));
        points.add(new Point(90,170));
        points.add(new Point(110,130));
        points.add(new Point(130,90));
        points.add(new Point(150,50));
        points.add(new Point(170,10));
        points.add(new Point(130,90));
        points.add(new Point(90,90));
        symbols.put("A", new Symbol(points));
        points.clear();


        points.add(new Point(10,10));
        points.add(new Point(150,10));
        points.add(new Point(170,50));
        points.add(new Point(150,90));
        points.add(new Point(10,90));
        points.add(new Point(150,90));
        points.add(new Point(170,130));
        points.add(new Point(150,170));
        points.add(new Point(10,170));
        points.add(new Point(10,10));
        symbols.put("B", new Symbol(points));
        points.clear();

        points.add(new Point(170,20));
        points.add(new Point(80,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        points.add(new Point(10,10));
        symbols.put("C", new Symbol(points));
        points.clear();

        symbols.put("D", new Symbol());

        symbols.put("E", new Symbol());

        symbols.put("F", new Symbol());

        symbols.put("G", new Symbol());

        symbols.put("H", new Symbol());

        symbols.put("I", new Symbol());

        symbols.put("J", new Symbol());

        symbols.put("K", new Symbol());

        symbols.put("L", new Symbol());

        symbols.put("M", new Symbol());

        symbols.put("N", new Symbol());

        symbols.put("O", new Symbol());

        symbols.put("P", new Symbol());

        symbols.put("Q", new Symbol());

        symbols.put("R", new Symbol());

        symbols.put("S", new Symbol());

        symbols.put("T", new Symbol());

        symbols.put("U", new Symbol());

        symbols.put("V", new Symbol());

        symbols.put("W", new Symbol());

        symbols.put("X", new Symbol());

        symbols.put("Y", new Symbol());

        symbols.put("Z", new Symbol());

=======
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Point;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private ImageView image, imageVide;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    float largeur = 0, hauteur = 0, downx = 0, downy = 0, upx = 0, upy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);

        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float largeur = currentDisplay.getWidth();
        float hauteur = currentDisplay.getHeight();

        bitmap = Bitmap.createBitmap((int) largeur, (int) hauteur, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(0.18f*largeur);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        imageVide.setImageBitmap(bitmap);

        //canvas.drawLine(0.25f*dw, 0.25f*dh, 0.5f*dw, 0.5f*dh, paint);

        imageVide.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawPoint(upx, upy, paint);
                imageVide.invalidate();
                System.out.println(downx + " " + downy);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
>>>>>>> bcf7d7fe69bcfd619b174f9230b266708077ef83
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
