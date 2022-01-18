package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"userId", "category", "subCategory"})
public class MemoryData {

    private int userId;
    @NonNull
    private String category;
    private int subCategory;
    private int difficulty;
    private int winStreak;
    private int loseStreak;

    public MemoryData(int userId, String category, int subCategory) {
        this.userId = userId;
        this.category = category;
        this.subCategory = subCategory;
        this.difficulty = 1;
        this.winStreak = 0;
        this.loseStreak = 0;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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
