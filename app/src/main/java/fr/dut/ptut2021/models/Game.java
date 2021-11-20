package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import fr.dut.ptut2021.database.CreateDatabase;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int image;

    //Constructor
    public Game(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Game(String name, int image, Theme theme) {
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
    public int getImage() {
        return image;
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

}
