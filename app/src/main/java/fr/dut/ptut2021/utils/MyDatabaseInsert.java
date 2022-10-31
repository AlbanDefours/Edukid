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
    private CreateDatabase db = null;
    private final String themeLettres = "Lettres", themeChiffres = "Chiffres";
    private static MyDatabaseInsert instance;

    public static MyDatabaseInsert getInstance() {
        if (instance == null) {
            instance = new MyDatabaseInsert();
        }
        return instance;
    }

    public void ajoutDatabase(Context context) {
        if (db == null)
            db = CreateDatabase.getInstance(context);

        createThemes();
        createGames();
        createSubGames();
        createWords();
        createCards(context);
    }

    public void createThemes() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertTheme(new Theme(themeLettres, R.drawable.logo_theme_lettres));
            db.appDao().insertTheme(new Theme(themeChiffres, R.drawable.logo_theme_chiffres));
        }
    }

    public void createGames() {
        if (db.appDao().tabGameIsEmpty()) {
            db.appDao().insertGame(new Game("Memory", themeLettres, R.drawable.logo_memory_lettre));
            //db.appDao().insertGame(new Game("Dessine", themeLettres, R.drawable.logo_drawonit_lettre));
            db.appDao().insertGame(new Game("Mot à trou", themeLettres, R.drawable.logo_wordwithhole_lettre));
            db.appDao().insertGame(new Game("Ecoute", themeLettres, R.drawable.logo_playwithsound_lettre));
            db.appDao().insertGame(new Game("Memory", themeChiffres, R.drawable.logo_memory));
            db.appDao().insertGame(new Game("Dessine", themeChiffres, R.drawable.logo_drawonit));
            db.appDao().insertGame(new Game("Ecoute", themeChiffres, R.drawable.logo_playwithsound));
        }
    }

    public void createSubGames() {
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

    public void createWords() {
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
        db.appDao().insertWord(new Word("NOUNOURS", R.drawable.image_nounours));
        db.appDao().insertWord(new Word("ABRICOT", R.drawable.image_abricot));
        db.appDao().insertWord(new Word("ANANAS", R.drawable.image_ananas));
        db.appDao().insertWord(new Word("AUBERGINE", R.drawable.image_aubergine));
        db.appDao().insertWord(new Word("BALANÇOIRE", R.drawable.image_balancoire));
        db.appDao().insertWord(new Word("CAMION", R.drawable.image_camion));
        db.appDao().insertWord(new Word("CERISE", R.drawable.image_cerise));
        db.appDao().insertWord(new Word("CITRON", R.drawable.image_citron));
        db.appDao().insertWord(new Word("LAPIN", R.drawable.image_lapin));
        db.appDao().insertWord(new Word("MOTO", R.drawable.image_moto));
        db.appDao().insertWord(new Word("PASTÈQUE", R.drawable.image_pasteque));
        db.appDao().insertWord(new Word("POUBELLE", R.drawable.image_poubelle));
        db.appDao().insertWord(new Word("POUPÉE", R.drawable.image_poupee));
        db.appDao().insertWord(new Word("ROUTE", R.drawable.image_route));
        db.appDao().insertWord(new Word("AVENTURIER", R.drawable.image_aventurier));
        db.appDao().insertWord(new Word("BALAI", R.drawable.image_balai));
        db.appDao().insertWord(new Word("BALEINE", R.drawable.image_baleine));
        db.appDao().insertWord(new Word("BÉBÉ", R.drawable.image_bebe));
        db.appDao().insertWord(new Word("BOUGIE", R.drawable.image_bougie));
        db.appDao().insertWord(new Word("CADEAU", R.drawable.image_cadeau));
        db.appDao().insertWord(new Word("CANARD", R.drawable.image_canard));
        db.appDao().insertWord(new Word("CARTE", R.drawable.image_carte));
        db.appDao().insertWord(new Word("CASQUE", R.drawable.image_casque));
        db.appDao().insertWord(new Word("CHAPEAU", R.drawable.image_chapeau));
        db.appDao().insertWord(new Word("CHAT", R.drawable.image_chat));
        db.appDao().insertWord(new Word("CHENILLE", R.drawable.image_chenille));
        db.appDao().insertWord(new Word("CHEVALIER", R.drawable.image_chevalier));
        db.appDao().insertWord(new Word("CHOCOLAT", R.drawable.image_chocolat));
        db.appDao().insertWord(new Word("CITROUILLE", R.drawable.image_citrouille));
        db.appDao().insertWord(new Word("CRAVATE", R.drawable.image_cravate));
        db.appDao().insertWord(new Word("DINOSAURE", R.drawable.image_dinosaure));
        db.appDao().insertWord(new Word("DRAGON", R.drawable.image_dragon));
        db.appDao().insertWord(new Word("ÉTOILE", R.drawable.image_etoile));
        db.appDao().insertWord(new Word("FANTÔME", R.drawable.image_fantome));
        db.appDao().insertWord(new Word("FÉE", R.drawable.image_fee));
        db.appDao().insertWord(new Word("FLEUR", R.drawable.image_fleur));
        db.appDao().insertWord(new Word("FLÛTE", R.drawable.image_flute));
        db.appDao().insertWord(new Word("GÂTEAU", R.drawable.image_gateau));
        db.appDao().insertWord(new Word("GIRAFE", R.drawable.image_girafe));
        db.appDao().insertWord(new Word("GOMME", R.drawable.image_gomme));
        db.appDao().insertWord(new Word("INSPECTEUR", R.drawable.image_inspecteur));
        db.appDao().insertWord(new Word("KOALA", R.drawable.image_koala));
        db.appDao().insertWord(new Word("LIT", R.drawable.image_lit));
        db.appDao().insertWord(new Word("LUNETTES", R.drawable.image_lunettes));
        db.appDao().insertWord(new Word("LUTIN", R.drawable.image_lutin));
        db.appDao().insertWord(new Word("MANTEAU", R.drawable.image_manteau));
        db.appDao().insertWord(new Word("MARCUS", R.drawable.image_marcus));
        db.appDao().insertWord(new Word("MASQUE", R.drawable.image_masque));
        db.appDao().insertWord(new Word("MONSTRE", R.drawable.image_monstre));
        db.appDao().insertWord(new Word("MONTAGNE", R.drawable.image_montagne));
        db.appDao().insertWord(new Word("NAIN", R.drawable.image_nain));
        db.appDao().insertWord(new Word("NINJA", R.drawable.image_ninja));
        db.appDao().insertWord(new Word("NUAGE", R.drawable.image_nuage));
        db.appDao().insertWord(new Word("OGRE", R.drawable.image_ogre));
        db.appDao().insertWord(new Word("ORDINATEUR", R.drawable.image_ordinateur));
        db.appDao().insertWord(new Word("PANDA", R.drawable.image_panda));
        db.appDao().insertWord(new Word("PAPILLON", R.drawable.image_papillon));
        db.appDao().insertWord(new Word("PARAPLUIE", R.drawable.image_parapluie));
        db.appDao().insertWord(new Word("PERCEUSE", R.drawable.image_perceuse));
        db.appDao().insertWord(new Word("PIANO", R.drawable.image_piano));
        db.appDao().insertWord(new Word("PIRATE", R.drawable.image_pirate));
        db.appDao().insertWord(new Word("PIZZA", R.drawable.image_pizza));
        db.appDao().insertWord(new Word("POISSON", R.drawable.image_poisson));
        db.appDao().insertWord(new Word("PRINCE", R.drawable.image_prince));
        db.appDao().insertWord(new Word("PRINCESSE", R.drawable.image_princesse));
        db.appDao().insertWord(new Word("RAQUETTE", R.drawable.image_raquette));
        db.appDao().insertWord(new Word("ROBE", R.drawable.image_robe));
        db.appDao().insertWord(new Word("ROBOT", R.drawable.image_robot));
        db.appDao().insertWord(new Word("SAC", R.drawable.image_sac));
        db.appDao().insertWord(new Word("SORCIÈRE", R.drawable.image_sorciere));
        db.appDao().insertWord(new Word("STELLA", R.drawable.image_stella));
        db.appDao().insertWord(new Word("TABLE", R.drawable.image_table));
        db.appDao().insertWord(new Word("TAMBOUR", R.drawable.image_tambour));
        db.appDao().insertWord(new Word("TAPIS", R.drawable.image_tapis));
        db.appDao().insertWord(new Word("TOUPIE", R.drawable.image_toupie));
        db.appDao().insertWord(new Word("TOURNEVIS", R.drawable.image_tournevis));
        db.appDao().insertWord(new Word("TRAIN", R.drawable.image_train));
        db.appDao().insertWord(new Word("VÉLO", R.drawable.image_velo));
        db.appDao().insertWord(new Word("VIOLON", R.drawable.image_violon));
        db.appDao().insertWord(new Word("ZOMBIE", R.drawable.image_zombie));
    }

    public void createCards(Context context) {
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