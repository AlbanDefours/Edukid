package fr.dut.ptut2021.models.database.log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameResultLog {

    @PrimaryKey(autoGenerate = true)
    private int gameResultLogId;
    private int gameId, subGameId, userId, stars, difficulty;
    private long endGameDate;

//subGame = -1 s'il n'y en a pas
    public GameResultLog(int gameId, int subGameId, int userId, int stars, int difficulty) {
        this.gameId = gameId;
        this.subGameId = subGameId;
        this.userId = userId;
        this.stars = stars;
        this.difficulty = difficulty;
        this.endGameDate = System.currentTimeMillis();
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

    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSubGameId() {
        return subGameId;
    }
    public void setSubGameId(int subGameId) {
        this.subGameId = subGameId;
    }

    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getEndGameDate() {
        return endGameDate;
    }
    public void setEndGameDate(long endGameDate) {
        this.endGameDate = endGameDate;
    }
}
