package fr.dut.ptut2021.models.database.app;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity (primaryKeys = {"gameName", "subGameName"})
public class GameSubGameCrossRef {
    @NonNull
    private String gameName, subGameName;

    public GameSubGameCrossRef(String gameName, String subGameName){
        this.gameName = gameName;
        this.subGameName = subGameName;
    }

    @NonNull
    public String getGameName() {
        return gameName;
    }

    public void setGameName(@NonNull String gameName) {
        this.gameName = gameName;
    }

    @NonNull
    public String getSubGameName() {
        return subGameName;
    }

    public void setSubGameName(@NonNull String subGameName) {
        this.subGameName = subGameName;
    }
}