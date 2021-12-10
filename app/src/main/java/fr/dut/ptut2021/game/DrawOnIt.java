package fr.dut.ptut2021.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.DataSymbol;
import fr.dut.ptut2021.models.Symbol;

public class DrawOnIt extends AppCompatActivity implements View.OnTouchListener {

    private ImageView image;
    private ImageView imageVide;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    float downx = 0, downy = 0, upx = 0, upy = 0;
    DataSymbol data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);

        image = findViewById(R.id.idImage_drawOnIt);
        imageVide = findViewById(R.id.idImageVide_drawOnIt);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();

        bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(70);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        imageVide.setImageBitmap(bitmap);

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
                //canvas.drawLine(downx, downy, upx, upy, paint);
                canvas.drawPoint(upx, upy, paint);
                //downx = event.getX();
                //downy = event.getY();
                imageVide.invalidate();
                System.out.println(downx + " " + downy);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("bite");

                /*File filename = new File(Environment.getExternalStorageDirectory(), "imagedsddsds.png") ;
                try (FileOutputStream out = new FileOutputStream(filename)) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }
}
