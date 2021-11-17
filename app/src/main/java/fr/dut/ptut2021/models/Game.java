package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Game {

    @PrimaryKey
    private int id;
    private String name;
    private int image;
    private ArrayList<Theme> listTheme;


    //Constructor
    public Game(String name, int image, ArrayList<Theme> listTheme) {
        this.name = name;
        this.image = image;
        this.listTheme = listTheme;
    }
    
    public Game(String name, int image, Theme theme) {
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
    public int getImage() {
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
    public void setImage(int image) {
        this.image = image;
    }
    public void setTheme(ArrayList<Theme> listTheme) {
        this.listTheme = listTheme;
    }

    public Boolean checkTheme(String nameTheme) {
        for (Theme theme : listTheme) {
            /*if (nameTheme == theme.getName()) {
                return true;
            }*/
        }
        return false;
    }

}
