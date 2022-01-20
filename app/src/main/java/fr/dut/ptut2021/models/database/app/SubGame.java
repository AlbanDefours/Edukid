package fr.dut.ptut2021.models.database.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubGame {

    @PrimaryKey(autoGenerate = true)
    private int subGameId;
    private String subGameName;
    private int gameId;
    private int subGameImage;

    public SubGame(String subGameName, int gameId, int subGameImage) {
        this.subGameName = subGameName;
        this.gameId = gameId;
        this.subGameImage = subGameImage;
    }

    public int getSubGameId() {
        return subGameId;
    }
    public void setSubGameId(int subGameId) {
        this.subGameId = subGameId;
    }

    public String getSubGameName() {
        return subGameName;
    }
    public void setSubGameName(String subGameName) {
        this.subGameName = subGameName;
    }

    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSubGameImage() {
        return subGameImage;
    }
    public void setSubGameImage(int subGameImage) {
        this.subGameImage = subGameImage;
    }
}
