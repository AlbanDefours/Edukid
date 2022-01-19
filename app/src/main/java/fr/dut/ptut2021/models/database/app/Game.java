package fr.dut.ptut2021.models.database.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int gameId;
    private String gameName;
    private int gameImage;

    //Constructor
    public Game(String gameName, int gameImage) {
        this.gameName = gameName;
        this.gameImage = gameImage;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameImage() {
        return gameImage;
    }

    public void setGameImage(int gameImage) {
        this.gameImage = gameImage;
    }
}