package fr.dut.ptut2021.models;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Game {

    @PrimaryKey
    private int id;
    private String name;
    private Drawable image;
    private ArrayList<Theme> listTheme;

    //Constructor
    public Game(int id, String name, Drawable image, ArrayList<Theme> listTheme) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.listTheme = listTheme;
    }
    public Game(int id, String name, Drawable image, Theme theme) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.listTheme = new ArrayList<>();
        this.listTheme.add(theme);
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
    public ArrayList<Theme> getTheme() {
        return listTheme;
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
    public void setTheme(ArrayList<Theme> listTheme) {
        this.listTheme = listTheme;
    }

    public Boolean checkTheme(String nameTheme) {
        for (Theme theme : listTheme) {
            if (nameTheme == theme.getName()) {
                return true;
            }
        }
        return false;
    }

}
