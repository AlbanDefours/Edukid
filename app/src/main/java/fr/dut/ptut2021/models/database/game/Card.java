package fr.dut.ptut2021.models.database.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Native;

@Entity
public class Card {

    @PrimaryKey
    @NonNull
    private String cardValue;
    private String type;
    private int drawableImage;

    public Card(String cardValue, String type, int drawableImage) {
        this.cardValue = cardValue;
        this.type = type;
        this.drawableImage = drawableImage;
    }

    public String getCardValue() {
        return cardValue;
    }
    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getDrawableImage() {
        return drawableImage;
    }
    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }
}
