package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.ThemeGameCrossRef;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        createDatabase();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (db.appDao().tabUserIsEmpty()) {
                    openUserEditPage();
                } else {
                    openUserMenuPage();
                }
            }
        }, 1000);
    }

    private void createDatabase() {
        db = CreateDatabase.getInstance(getApplicationContext());
        createThemes();
        createGames();
        createThemeGamesCross();
    }

    //Here to add/update/delete Theme
    private void createThemes() {
        if (db.appDao().tabThemeIsEmpty()) {
            db.appDao().insertTheme(new Theme("Lettres", R.drawable.lettres));
            db.appDao().insertTheme(new Theme("Chiffres", R.drawable.chiffres));
        }
    }

    //Here to add/update/delete Game
    private void createGames() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertGame(new Game("Memory", R.drawable.chiffres));
            db.appDao().insertGame(new Game("Memory", R.drawable.lettres));
            db.appDao().insertGame(new Game("DrawOnIt", R.drawable.lettres));
            db.appDao().insertGame(new Game("DrawOnIt", R.drawable.chiffres));
            db.appDao().insertGame(new Game("Syllabe", R.drawable.lettres));
            db.appDao().insertGame(new Game("Suite chiffre", R.drawable.chiffres));
        }
    }

    //Here to set theme to Game
    private void createThemeGamesCross() {
        if (db.appDao().tabThemeGameIsEmpty()) {
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("DrawOnIt", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("DrawOnIt", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Syllabe", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Suite chiffre", "Chiffres"));
        }
    }

    private void openUserMenuPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    private void openUserEditPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }
}