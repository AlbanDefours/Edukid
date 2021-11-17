package fr.dut.ptut2021.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.theme.ThemeAdapter;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Theme;

public class ThemeMenu extends AppCompatActivity {

    private CreateDatabase db = null;
    private List<Theme> listTheme = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_menu);

        db = CreateDatabase.getInstance(ThemeMenu.this);

        if (!db.themeDao().tabThemeIsEmpty()) {
            listTheme = db.themeDao().getAllThemes();
        } else {
            db.themeDao().createTheme(new Theme("Lettres", R.drawable.lettres));
            db.themeDao().createTheme(new Theme("Chiffres", R.drawable.chiffres));
        }

        RecyclerView recyclerViewListTheme = findViewById(R.id.recyclerview_theme);
        recyclerViewListTheme.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListTheme.setAdapter(new ThemeAdapter(getApplicationContext(), listTheme));

        recyclerViewListTheme.addOnItemTouchListener(
            new RecyclerItemClickListener(getApplicationContext(), recyclerViewListTheme, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startGameMenu(position);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                }
            })
        );
    }

    private void startGameMenu(int position) {
        Intent intent = new Intent().setClass(getApplicationContext(), GameMenu.class);
        intent.putExtra("themeName", listTheme.get(position).getName());
        startActivity(intent);
    }
}