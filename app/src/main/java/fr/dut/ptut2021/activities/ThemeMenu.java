package fr.dut.ptut2021.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import fr.dut.ptut2021.R;

public class ThemeMenu extends AppCompatActivity {

    private RecyclerView listTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_menu);

        listTheme = findViewById(R.id.recyclerview_theme);


    }
}