package fr.dut.ptut2021.models.stats.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (primaryKeys = {"userId", "word", "syllable"})
public class WordWithHoleData {

    @NonNull
    private int userId;
    @NonNull
    private String word, syllable;
    private int image, win, winStreak, lose, loseStreak;
    private boolean lastUsed;

    public WordWithHoleData(int userId, String word, String syllable, int image) {
        this.userId = userId;
        this.word = word;
        this.syllable = syllable;
        this.image = image;
        this.win = 0;
        this.winStreak = 0;
        this.lose = 0;
        this.loseStreak = 0;
        this.lastUsed = false;
    }

    //Getter & Setter
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public String getSyllable() {
        return syllable;
    }
    public void setSyllable(String syllable) {
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

    public boolean isLastUsed() {
        return lastUsed;
    }
    public void setLastUsed(boolean lastUsed) {
        this.lastUsed = lastUsed;
    }

}
