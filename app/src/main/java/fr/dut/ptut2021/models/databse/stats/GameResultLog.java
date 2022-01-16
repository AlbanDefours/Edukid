package fr.dut.ptut2021.models.databse.stats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameResultLog {

    @PrimaryKey(autoGenerate = true)
    private int gameResultLogId;
    private String gameName;
    private int stars;
    private long endGameDate;

    public GameResultLog(int gameResultLogId, String gameName, int stars) {
        this.gameResultLogId = gameResultLogId;
        this.gameName = gameName;
        this.stars = stars;
        this.endGameDate = System.currentTimeMillis();
        //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT
    }

    public int getGameResultLogId() {
        return gameResultLogId;
    }

    public void setGameResultLogId(int gameResultLogId) {
        this.gameResultLogId = gameResultLogId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public long getEndGameDate() {
        return endGameDate;
    }

    public void setEndGameDate(long endGameDate) {
        this.endGameDate = endGameDate;
    }
}
