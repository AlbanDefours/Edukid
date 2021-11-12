package fr.dut.ptut2021.bdd;

import android.graphics.drawable.Drawable;

public class Jeu extends Theme{
    private int idJeu;
    private String nomJeu;
    private Drawable imageJeu;


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
