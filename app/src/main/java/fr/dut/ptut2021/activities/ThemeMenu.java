package fr.dut.ptut2021.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.theme.ThemeAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Theme;

public class ThemeMenu extends AppCompatActivity {

    private CreateDatabase db = null;
    private RecyclerView recyclerViewListTheme;
    private List<Theme> listTheme = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_menu);

        createAndGetDatabase();
        createRecyclerView();

        recyclerViewListTheme.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListTheme, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            v.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
                        else
                            v.vibrate(35);
                        saveUserNameSahredPref(position);
                        startGameMenu(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void createAndGetDatabase() {
        db = CreateDatabase.getInstance(ThemeMenu.this);

        if (!db.appDao().tabThemeIsEmpty()) {
            listTheme = db.appDao().getAllThemes();
        } else {
            db.appDao().insertTheme(new Theme("Lettres", R.drawable.lettres));
            db.appDao().insertTheme(new Theme("Chiffres", R.drawable.chiffres));
        }
    }

    private void createRecyclerView() {
        recyclerViewListTheme = findViewById(R.id.recyclerview_theme);
        recyclerViewListTheme.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListTheme.setAdapter(new ThemeAdapter(getApplicationContext(), listTheme));
    }

    private void startGameMenu(int position) {
        Intent intent = new Intent().setClass(getApplicationContext(), GameMenu.class);
        startActivity(intent);
    }

    private void saveUserNameSahredPref(int position){
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("themeName", listTheme.get(position).getThemeName());
        editor.commit();
    }
}