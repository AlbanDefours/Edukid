package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.ThemeGameCrossRef;
import fr.dut.ptut2021.models.database.app.Word;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        textAnimation();
        createDatabase();

        new Handler().postDelayed(() -> {
            if (db.appDao().tabUserIsEmpty()) {
                openUserEditPage();
            } else {
                openUserMenuPage();
            }
        }, 1500);
    }

    //TODO Faire bing bing bing bing de la gauche et TADA !
    private void textAnimation() {
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(findViewById(R.id.applicationName));
    }

    private void createDatabase() {
        db = CreateDatabase.getInstance(getApplicationContext());
        createThemes();
        createGames();
        createThemeGamesCross();
        createWords();
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
            db.appDao().insertGame(new Game("Memory", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Dessine", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Mot à trou", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Ecoute", R.drawable.memory_icon));
        }
    }

    //Here to set theme to Game
    private void createThemeGamesCross() {
        if (db.appDao().tabThemeGameIsEmpty()) {
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Memory", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Dessine", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Dessine", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Mot à trou", "Lettres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Ecoute", "Chiffres"));
            db.appDao().insertThemeGame(new ThemeGameCrossRef("Ecoute", "Lettres"));
        }
    }

    //Here to add Words with images
    private void createWords() {
        db.appDao().insertWord(new Word("AVION", R.drawable.image_avion));
        db.appDao().insertWord(new Word("MAISON", R.drawable.image_maison));
        db.appDao().insertWord(new Word("POULE", R.drawable.image_poule));
        db.appDao().insertWord(new Word("BOUCHE", R.drawable.image_bouche));
        db.appDao().insertWord(new Word("LIVRE", R.drawable.image_livre));
        db.appDao().insertWord(new Word("VACHE", R.drawable.image_vache));
        db.appDao().insertWord(new Word("TOMATE", R.drawable.image_tomate));
        db.appDao().insertWord(new Word("CHIEN", R.drawable.image_chien));
        db.appDao().insertWord(new Word("ARBRE", R.drawable.image_arbre));
        db.appDao().insertWord(new Word("BALLON", R.drawable.image_ballon));
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