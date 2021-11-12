package fr.dut.ptut2021.bdd;

import android.graphics.drawable.Drawable;

public class Theme {
    private int idTheme;
    private String nomTheme;
    private Drawable imageTheme;


    //Getter
    public int getIdTheme() {
        return idTheme;
    }
    public String getNomTheme() {
        return nomTheme;
    }
    public Drawable getImageTheme() {
        return imageTheme;
    }

    //Setter
    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }
    public void setNomTheme(String nomTheme) {
        this.nomTheme = nomTheme;
    }
    public void setImageTheme(Drawable imageTheme) {
        this.imageTheme = imageTheme;
    }

}
