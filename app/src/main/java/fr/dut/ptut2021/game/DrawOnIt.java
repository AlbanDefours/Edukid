package fr.dut.ptut2021.game;

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
