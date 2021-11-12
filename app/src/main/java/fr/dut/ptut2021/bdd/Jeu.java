package fr.dut.ptut2021.bdd;

import android.graphics.drawable.Drawable;

public class Jeu {
    private int idJeu;
    private String nomJeu;
    private Drawable imageJeu;
    private Theme theme;

    //Constructor
    public Jeu(int idJeu, String nomJeu, Drawable imageJeu, Theme theme) {
        this.idJeu = idJeu;
        this.nomJeu = nomJeu;
        this.imageJeu = imageJeu;
        this.theme = theme;
    }

    //Getter
    public int getIdJeu() {
        return idJeu;
    }
    public String getNomJeu() {
        return nomJeu;
    }
    public Drawable getImageJeu() {
        return imageJeu;
    }

    //Setter
    public void setId(int idJeu) {
        this.idJeu = idJeu;
    }
    public void setNom(String nomJeu) {
        this.nomJeu = nomJeu;
    }
    public void setImage(Drawable imageJeu) {
        this.imageJeu = imageJeu;
    }
}
