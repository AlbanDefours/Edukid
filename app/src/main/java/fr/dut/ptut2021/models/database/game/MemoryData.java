package fr.dut.ptut2021.models.database.game;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MemoryData {

    @PrimaryKey
    private int userId;
    private int difficultyLettres, difficultyChiffres;
    private int winStreak, loseStreak;

    public MemoryData(int userId) {
        this.userId = userId;
        this.difficultyChiffres = 0;
        this.difficultyLettres = 0;
        this.winStreak = 0;
        this.loseStreak = 0;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDifficultyLettres() {
        return difficultyLettres;
    }
    public void setDifficultyLettres(int difficultyLettres) {
        this.difficultyLettres = difficultyLettres;
    }

    public int getDifficultyChiffres() {
        return difficultyChiffres;
    }
    public void setDifficultyChiffres(int difficultyChiffres) {
        this.difficultyChiffres = difficultyChiffres;
    }

    public int getWinStreak() {
        return winStreak;
    }
    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    public int getLoseStreak() {
        return loseStreak;
    }
    public void setLoseStreak(int loseStreak) {
        this.loseStreak = loseStreak;
    }
}
