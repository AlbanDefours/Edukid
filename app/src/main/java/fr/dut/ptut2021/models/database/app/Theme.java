package fr.dut.ptut2021.models.database.app;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Theme {

    @PrimaryKey
    @NonNull
    private String themeName;
    private int themeImage;

    //Constructor
    public Theme(String themeName, int themeImage) {
        this.themeName = themeName;
        this.themeImage = themeImage;
    }

    public String getThemeName() {
        return themeName;
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
}
