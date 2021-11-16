package fr.dut.ptut2021.models;

import android.graphics.drawable.Drawable;

public class Theme {
    private int id;
    private String name;
    private Drawable image;

    public enum AllTheme {
        LETTER, NUMBER;
    }

    //Constructor
    public Theme(int id, String name, Drawable image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    //Getter
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Drawable getImage() {
        return image;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }

}
