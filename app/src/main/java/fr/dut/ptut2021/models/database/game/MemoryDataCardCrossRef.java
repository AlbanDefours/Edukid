package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"cardValue", "userId", "category", "subCategory"})
public class MemoryDataCardCrossRef {

    @NonNull
    private String cardValue;
    private int userId;
    private String category;
    private int subCategory;
    private int used;

    public MemoryDataCardCrossRef(String cardValue, int userId, String category, int subCategory) {
        this.cardValue = cardValue;
        this.userId = userId;
        this.category = category;
        this.subCategory = subCategory;
        this.used = 0;
    }

    @NonNull
    public String getCardValue() {
        return cardValue;
    }
    public void setCardValue(@NonNull String cardValue) {
        this.cardValue = cardValue;
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

    public int getUsed() {
        return used;
    }
    public void setUsed(int used) {
        this.used = used;
    }
}
