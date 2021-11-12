package fr.dut.ptut2021.bdd;

import android.graphics.drawable.Drawable;

public class Profil {
    private int idProfil;
    private String nomProfil;
    private Drawable imageProfil;


    //Getter
    public int getIdProfil() {
        return idProfil;
    }
    public String getNomProfil() {
        return nomProfil;
    }
    public Drawable getImageProfil() {
        return imageProfil;
    }

    //Setter
    public void setId(int idProfil) {
        this.idProfil = idProfil;
    }
    public void setNom(String nomProfil) {
        this.nomProfil = nomProfil;
    }
    public void setImage(Drawable imageProfil) {
        this.imageProfil = imageProfil;
    }

}
