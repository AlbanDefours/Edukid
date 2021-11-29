package fr.dut.ptut2021.models.stats.game;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordWithHoleStats {

    @PrimaryKey(autoGenerate = true)
    private int wordWithHoleId;
    private int userId;
    private String word, syllable;
    private int win, winStreak, lose, loseStreak;
    private boolean lastUsed;

    public WordWithHoleStats(int userId, String word, String syllable) {
        this.userId = userId;
        this.word = word;
        this.syllable = syllable;
        this.win = 0;
        this.winStreak = 0;
        this.lose = 0;
        this.loseStreak = 0;
        this.lastUsed = false;
    }

    //Getter & Setter
    public int getWordWithHoleId() {
        return wordWithHoleId;
    }
    public void setWordWithHoleId(int wordWithHoleId) {
        this.wordWithHoleId = wordWithHoleId;
    }

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
