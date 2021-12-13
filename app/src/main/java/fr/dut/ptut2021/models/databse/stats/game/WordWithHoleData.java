package fr.dut.ptut2021.models.databse.stats.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (primaryKeys = {"userId", "word", "syllable"})
public class WordWithHoleData {

    private int dataId, userId;
    @NonNull
    private String word, syllable;
    private int image, win, winStreak, lose, loseStreak, lastUsed;

    public WordWithHoleData(int dataId, int userId, @NonNull String word, @NonNull String syllable, int image) {
        this.dataId = dataId;
        this.userId = userId;
        this.word = word;
        this.syllable = syllable;
        this.image = image;
        this.win = 0;
        this.winStreak = 0;
        this.lose = 0;
        this.loseStreak = 0;
        this.lastUsed = -1;
    }

    //Getter & Setter
    public int getDataId() {
        return dataId;
    }
    public void setDataId(int dataId) {
        this.userId = dataId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getWord() {
        return word;
    }
    public void setWord(@NonNull String word) {
        this.word = word;
    }

    @NonNull
    public String getSyllable() {
        return syllable;
    }
    public void setSyllable(@NonNull String syllable) {
        this.syllable = syllable;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
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
