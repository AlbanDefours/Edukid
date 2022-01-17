package fr.dut.ptut2021.models.databse.stats.log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameResultLog {

    @PrimaryKey(autoGenerate = true)
    private int gameResultLogId;
    private String gameName;
    private int userId, stars;
    private long endGameDate;

    public GameResultLog(String gameName, int userId, int stars) {
        this.gameName = gameName;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
