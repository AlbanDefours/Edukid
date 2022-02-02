package fr.dut.ptut2021.utils;

import android.content.Context;
import android.util.Log;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.Card;

public class MyDatabaseInsert {
    static CreateDatabase db = null;
    static final String themeLettres = "Lettres";
    static final String themeChiffres = "Chiffres";

    public static void ajoutDatabase(Context context) {
        if (db == null)
            db = CreateDatabase.getInstance(context);

        createThemes();
        createGames();
        createSubGames();
        createWords();
        createCards(context);
    }

    public static void createThemes() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertTheme(new Theme(themeLettres, R.drawable.logo_theme_lettres));
            db.appDao().insertTheme(new Theme(themeChiffres, R.drawable.logo_theme_chiffres));
        }
    }

    public static void createGames() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertGame(new Game("Memory", themeLettres, R.drawable.logo_memory_lettre));
            db.appDao().insertGame(new Game("Dessine", themeLettres, R.drawable.logo_drawonit_lettre));
            db.appDao().insertGame(new Game("Mot à trou", themeLettres, R.drawable.logo_wordwithhole_lettre));
            db.appDao().insertGame(new Game("Ecoute", themeLettres, R.drawable.logo_playwithsound_lettre));
            db.appDao().insertGame(new Game("Memory", themeChiffres, R.drawable.logo_memory));
            db.appDao().insertGame(new Game("Dessine", themeChiffres, R.drawable.logo_drawonit));
            db.appDao().insertGame(new Game("Ecoute", themeChiffres, R.drawable.logo_playwithsound));
        }
    }

    public static void createSubGames() {
        if (db.appDao().tabSubGameIsEmpty()) {

            db.appDao().insertSubGame(new SubGame("Niveau 1", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_majuscule_majuscule));
            db.appDao().insertSubGame(new SubGame("Niveau 2", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_majuscule_majuscule_diff));
            db.appDao().insertSubGame(new SubGame("Niveau 3", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_majuscule_miniscule));
            db.appDao().insertSubGame(new SubGame("Niveau 4", db.appDao().getGameId("Memory", themeLettres), R.drawable.memory_majuscule_miniscule_diff));
            db.appDao().insertSubGame(new SubGame("Niveau 1", db.appDao().getGameId("Memory", themeChiffres), R.drawable.logo_memory_img_img));
            db.appDao().insertSubGame(new SubGame("Niveau 2", db.appDao().getGameId("Memory", themeChiffres), R.drawable.logo_memory_img_imgdiff));
            db.appDao().insertSubGame(new SubGame("Niveau 3", db.appDao().getGameId("Memory", themeChiffres), R.drawable.logo_memory_chiffre_chiffre));
            db.appDao().insertSubGame(new SubGame("Niveau 4", db.appDao().getGameId("Memory", themeChiffres), R.drawable.logo_memory_img_chiffre));

        }
    }

    public static void createWords() {
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

    public static void createCards(Context context) {
        db.gameDao().insertCard(new Card("1", "Chiffres", R.drawable.number_one));
        db.gameDao().insertCard(new Card("2", "Chiffres", R.drawable.number_two));
        db.gameDao().insertCard(new Card("3", "Chiffres", R.drawable.number_three));
        db.gameDao().insertCard(new Card("4", "Chiffres", R.drawable.number_four));
        db.gameDao().insertCard(new Card("5", "Chiffres", R.drawable.number_five));
        db.gameDao().insertCard(new Card("6", "Chiffres", R.drawable.number_six));
        db.gameDao().insertCard(new Card("7", "Chiffres", R.drawable.number_seven));
        db.gameDao().insertCard(new Card("8", "Chiffres", R.drawable.number_eight));
        db.gameDao().insertCard(new Card("9", "Chiffres", R.drawable.number_nine));


        String[] alphabetList = context.getResources().getStringArray(R.array.alphabet);
        for(int i=0;i<alphabetList.length;i++){
            db.gameDao().insertCard(new Card(alphabetList[i], "Lettres", 0));
        }
    }
}