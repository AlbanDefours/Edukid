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
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.utils.GlobalUtils;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;
    final String themeLettres = "Lettres";
    final String themeChiffres = "Chiffres";

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
        createSubGames();
        createWords();
        createCards();
    }

    //Here to add/update/delete Theme
    private void createThemes() {
        db.appDao().insertTheme(new Theme(themeLettres, R.drawable.lettres));
        db.appDao().insertTheme(new Theme(themeChiffres, R.drawable.chiffres));
    }

    //Here to add/update/delete Game
    private void createGames() {
        db.appDao().insertGame(new Game("Memory", themeLettres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Dessine", themeLettres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Mot à trou", themeLettres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Ecoute", themeLettres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Memory", themeChiffres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Dessine", themeChiffres, R.drawable.memory_icon));
        db.appDao().insertGame(new Game("Ecoute", themeChiffres, R.drawable.memory_icon));
    }

    //Here to add SubGame
    private void createSubGames() {
        db.appDao().insertSubGame(new SubGame("Image / Image", db.appDao().getGameId("Memory", themeChiffres), R.drawable.memory_img_img));
        db.appDao().insertSubGame(new SubGame("Image / Image différente", db.appDao().getGameId("Memory", themeChiffres), R.drawable.memory_img_imgdiff));
        db.appDao().insertSubGame(new SubGame("Chiffre / Chiffre", db.appDao().getGameId("Memory", themeChiffres), R.drawable.memory_chiffre_chiffre));
        db.appDao().insertSubGame(new SubGame("Image / Chiffre", db.appDao().getGameId("Memory", themeChiffres), R.drawable.memory_img_chiffre));
        db.appDao().insertSubGame(new SubGame("Memory1", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_icon));
        db.appDao().insertSubGame(new SubGame("Memory2", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_icon));
        db.appDao().insertSubGame(new SubGame("Memory3", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_icon));
        db.appDao().insertSubGame(new SubGame("Memory4", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_icon));
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

    private void createCards() {
        db.gameDao().insertCard(new Card("1","Chiffres",R.drawable.number_one));
        db.gameDao().insertCard(new Card("2","Chiffres",R.drawable.number_two));
        db.gameDao().insertCard(new Card("3","Chiffres",R.drawable.number_three));
        db.gameDao().insertCard(new Card("4","Chiffres",R.drawable.number_four));
        db.gameDao().insertCard(new Card("5","Chiffres",R.drawable.number_five));
        db.gameDao().insertCard(new Card("6","Chiffres",R.drawable.number_six));
        db.gameDao().insertCard(new Card("7","Chiffres",R.drawable.number_seven));
        db.gameDao().insertCard(new Card("8","Chiffres",R.drawable.number_eight));
        db.gameDao().insertCard(new Card("9","Chiffres",R.drawable.number_nine));
    }

    private void openUserEditPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }
}