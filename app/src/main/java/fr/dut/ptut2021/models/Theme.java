package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Theme {

    @PrimaryKey(autoGenerate = true)
    private int themeId;
    private String themeName;
    private int themeImage;

    //Constructor
    public Theme(String themeName, int themeImage) {
        this.themeName = themeName;
        this.themeImage = themeImage;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }
    public int getId() {
        return id;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getThemeImage() {
        return themeImage;
    }

    public void setThemeImage(int themeImage) {
        this.themeImage = themeImage;
    }
    public void setId(int id) {
        this.id = id;
    }
}
