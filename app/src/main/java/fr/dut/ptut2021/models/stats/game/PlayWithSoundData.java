package fr.dut.ptut2021.models.stats.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity (primaryKeys = {"userId", "result"})
public class PlayWithSoundData {

    private int dataId, userId;
    @NonNull
    private String result;
    private String theme;
    private int difficulty, win, winStreak, lose, loseStreak, image, lastUsed;

    public PlayWithSoundData(int dataId, int userId, @NonNull String result, String theme, int difficulty, int image) {
        this.dataId = dataId;
        this.userId = userId;
        this.result = result;
        this.theme = theme;
        this.difficulty = difficulty;
        this.win = 0;
        this.winStreak = 0;
        this.lose = 0;
        this.loseStreak = 0;
        this.image = image;
        this.lastUsed = -1;
    }

    public int getDataId() {
        return dataId;
    }
    public void setDataUserId(int dataId) {
        this.dataId = dataId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getResult() {
        return result;
    }
    public void setResult(@NonNull String result) {
        this.result = result;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getWin() {
        return win;
    }
    public void setWin(int win) {
        this.win = win;
    }

    public int getWinStreak() {
        return winStreak;
    }
    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    public int getLose() {
        return lose;
    }
    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getLoseStreak() {
        return loseStreak;
    }
    public void setLoseStreak(int loseStreak) {
        this.loseStreak = loseStreak;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public int getLastUsed() {
        return lastUsed;
    }
    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }
}
