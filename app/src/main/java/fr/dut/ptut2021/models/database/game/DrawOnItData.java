package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "draw"})
public class DrawOnItData {

    private int userId;
    @NonNull
    private String draw, themeName;
    private long touchTime;
    private int difficulty, win, winStreak, lose, loseStreak, lastUsed;

    public DrawOnItData(int userId, @NonNull String draw, String themeName, int difficulty) {
        this.userId = userId;
        this.draw = draw;
        this.touchTime = 0;
        this.difficulty = difficulty;
        this.win = 0;
        this.winStreak = 0;
        this.lose = 0;
        this.loseStreak = 0;
        this.lastUsed = -1;
        this.themeName = themeName;
    }

    @NonNull
    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(@NonNull String themeName) {
        this.themeName = themeName;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDraw() {
        return draw;
    }
    public void setDraw(@NonNull String draw) {
        this.draw = draw;
    }

    public long getTouchTime() {
        return touchTime;
    }
    public void setTouchTime(long touchTime) {
        this.touchTime = touchTime;
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

    public int getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }
}
