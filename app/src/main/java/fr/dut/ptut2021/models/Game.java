package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int gameId;
    private String name;
    private int image;

    //Constructor
    public Game(String name, int image) {
        this.name = name;
        this.image = image;
    }

    //Getter
    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
