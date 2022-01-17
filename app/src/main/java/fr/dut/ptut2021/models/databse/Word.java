package fr.dut.ptut2021.models.databse;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {

    @PrimaryKey
    @NonNull
    private String word;
    private int image;

    public Word(String word, int image) {
        this.word = word;
        this.image = image;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }
}
