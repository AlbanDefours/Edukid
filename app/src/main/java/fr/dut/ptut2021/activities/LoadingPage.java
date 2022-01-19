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
import fr.dut.ptut2021.models.database.app.GameSubGameCrossRef;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.ThemeGameCrossRef;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.utils.GlobalUtils;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        textAnimation();
        createDatabase();

        new Handler().postDelayed(() -> {
            if (db.appDao().tabUserIsEmpty())
                openUserEditPage();
            else
                GlobalUtils.startPage(this, "UserMenu", true, false);
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
        createSubGames();
        createGameSubGamesCross();
        createWords();
        createCards();
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
            db.appDao().insertGame(new Game("Memory", R.drawable.logo_memory));
            db.appDao().insertGame(new Game("Dessine", R.drawable.logo_drawonit));
            db.appDao().insertGame(new Game("Mot à trou", R.drawable.memory_icon));
            db.appDao().insertGame(new Game("Ecoute", R.drawable.logo_playwithsound));
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

    //Here to add SubGame
    private void createSubGames() {
        if (db.appDao().tabSubGameIsEmpty()) {
            db.appDao().insertSubGame(new SubGame("Memory1", R.drawable.img_img));
            db.appDao().insertSubGame(new SubGame("Memory2", R.drawable.img_imgdiff));
            db.appDao().insertSubGame(new SubGame("Memory3", R.drawable.chiffre_chiffre));
            db.appDao().insertSubGame(new SubGame("Memory4", R.drawable.img_chiffre));
        }
    }

    //Here to set Game to SubGame
    private void createGameSubGamesCross() {
        if (db.appDao().tabGameSubGameIsEmpty()) {
            db.appDao().insertGameSubGame(new GameSubGameCrossRef("Memory", "Memory1"));
            db.appDao().insertGameSubGame(new GameSubGameCrossRef("Memory", "Memory2"));
            db.appDao().insertGameSubGame(new GameSubGameCrossRef("Memory", "Memory3"));
            db.appDao().insertGameSubGame(new GameSubGameCrossRef("Memory", "Memory4"));
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
            db.appDao().insertWord(new Word("BATEAU", R.drawable.image_bateau));
            db.appDao().insertWord(new Word("BOUTEILLE", R.drawable.image_bouteille));
            db.appDao().insertWord(new Word("CAROTTE", R.drawable.image_carotte));
            db.appDao().insertWord(new Word("CHAISE", R.drawable.image_chaise));
            db.appDao().insertWord(new Word("CHEVAL", R.drawable.image_cheval));
            db.appDao().insertWord(new Word("LION", R.drawable.image_lion));
            db.appDao().insertWord(new Word("POMME", R.drawable.image_pomme));
            db.appDao().insertWord(new Word("SOLEIL", R.drawable.image_soleil));
            db.appDao().insertWord(new Word("TÉLÉPHONE", R.drawable.image_telephone));
            db.appDao().insertWord(new Word("VOITURE", R.drawable.image_voiture));
            db.appDao().insertWord(new Word("ZÈBRE", R.drawable.image_zebre));
    }

    private void createCards(){
        db.gameDao().insertCard(new Card("1","Chiffres",R.drawable.one));
        db.gameDao().insertCard(new Card("2","Chiffres",R.drawable.two));
        db.gameDao().insertCard(new Card("3","Chiffres",R.drawable.three));
        db.gameDao().insertCard(new Card("4","Chiffres",R.drawable.four));
        db.gameDao().insertCard(new Card("5","Chiffres",R.drawable.five));
        db.gameDao().insertCard(new Card("6","Chiffres",R.drawable.six));
        db.gameDao().insertCard(new Card("7","Chiffres",R.drawable.seven));
        db.gameDao().insertCard(new Card("8","Chiffres",R.drawable.eight));
        db.gameDao().insertCard(new Card("9","Chiffres",R.drawable.nine));
    }

    private void openUserEditPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }
}