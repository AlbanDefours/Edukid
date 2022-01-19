package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"cardValue", "userId"})
public class MemoryDataCardCrossRef {

    @NonNull
    private String cardValue;
    private int userId;
    private int used;

    public MemoryDataCardCrossRef(String cardValue, int userId) {
        this.cardValue = cardValue;
        this.userId = userId;
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

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}