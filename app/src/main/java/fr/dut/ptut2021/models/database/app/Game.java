package fr.dut.ptut2021.models.database.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int gameId;
    private String gameName;
    private String themeName;
    private int gameImage;

    //Constructor
    public Game(String gameName, String themeName, int gameImage) {
        this.gameName = gameName;
        this.themeName = themeName;
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

    public String getThemeName() {
        return themeName;
    }
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getGameImage() {
        return gameImage;
    }
    public void setGameImage(int gameImage) {
        this.gameImage = gameImage;
    }
}