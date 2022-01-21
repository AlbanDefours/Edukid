package fr.dut.ptut2021.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Symbol;

public class DrawOnIt extends AppCompatActivity {

    private ImageView image;

    private Map<String, Symbol> symbols = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_on_it);

        image = findViewById(R.id.idImage_drawOnIt);

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
        symbols.put("A", new Symbol());
        symbols.put("B", new Symbol());
        symbols.put("C", new Symbol());
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
        symbols.put("K", new Symbol());
        symbols.put("R", new Symbol());
        symbols.put("S", new Symbol());
        symbols.put("T", new Symbol());
        symbols.put("U", new Symbol());
        symbols.put("V", new Symbol());
        symbols.put("W", new Symbol());
        symbols.put("X", new Symbol());
        symbols.put("Y", new Symbol());
        symbols.put("Z", new Symbol());

    }
}

