package fr.dut.ptut2021.models.database.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubGame {

    @PrimaryKey(autoGenerate = true)
    private int subGameId;
    private String subGameName;
    private int subGameImage;

    public SubGame(String subGameName, int subGameImage) {
        this.subGameName = subGameName;
        this.subGameImage = subGameImage;
    }

    public int getSubGameId() {
        return subGameId;
    }

    public void setSubGameId(int subGameId) {
        this.subGameId = subGameId;
    }

    public String getSubGameName() {
        return subGameName;
    }

    public void setSubGameName(String subGameName) {
        this.subGameName = subGameName;
    }

    public int getSubGameImage() {
        return subGameImage;
    }

    public void setSubGameImage(int subGameImage) {
        this.subGameImage = subGameImage;
    }
}
