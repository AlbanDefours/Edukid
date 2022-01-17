package fr.dut.ptut2021.models.databse;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity (primaryKeys = {"gameName", "themeName"})
public class ThemeGameCrossRef {
    @NonNull
    private String gameName, themeName;

    public ThemeGameCrossRef(String gameName, String themeName){
        this.gameName = gameName;
        this.themeName = themeName;
    }

    @NonNull
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @NonNull
    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}