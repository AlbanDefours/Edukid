package fr.dut.ptut2021.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/*@Entity(foreignKeys = {
        @ForeignKey(
                entity = Theme.class,
                parentColumns = "id",
                childColumns = "themeId"
        ),
        @ForeignKey(
                entity = Game.class,
                parentColumns = "id",
                childColumns = "themeId"
        )
})*/
@Entity
public class ThemeGame {

    @PrimaryKey(autoGenerate = true)
    private int id;

    //@ColumnInfo(name="themeId")
    private int themeId;

    //@ColumnInfo(name="gameId")
    private int gameId;

    //Constructor
    public ThemeGame(int themeId, int gameId) {
        this.themeId = themeId;
        this.gameId = gameId;
    }

    //Getter
    public int getId() {
        return id;
    }
    public int getThemeId() {
        return themeId;
    }
    public int getGameId() {
        return gameId;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

}
