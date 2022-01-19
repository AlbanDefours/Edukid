package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"cardValue", "userId", "subCategory"})
public class MemoryDataCardCrossRef {

    @NonNull
    private String cardValue;
    private int userId;
    private int subCategory;
    private int used;

    public MemoryDataCardCrossRef(String cardValue, int userId, int subCategory) {
        this.cardValue = cardValue;
        this.userId = userId;
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
