package fr.dut.ptut2021.models.database.log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameLog {

    @PrimaryKey(autoGenerate = true)
    private int gameLogId;
    private int gameId, subGameId;
    //Id de la ligne correspondante (de la table gameName)
    private int tableRowId;
    private long gameLogDate;
    private boolean win;
    private int extraTry;

    //subGame = -1 s'il n'y en a pas
    public GameLog(int gameId, int subGameId, int tableRowId, boolean win, int extraTry) {
        this.gameId = gameId;
        this.subGameId = subGameId;
        this.tableRowId = tableRowId;
        this.gameLogDate = System.currentTimeMillis();
        this.win = win;
        this.extraTry = extraTry;
    }


    //Getter & Setter
    public int getGameLogId() {
        return gameLogId;
    }
    public void setGameLogId(int gameLogId) {
        this.gameLogId = gameLogId;
    }

    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSubGameId() {
        return subGameId;
    }
    public void setSubGameId(int subGameId) {
        this.subGameId = subGameId;
    }

    public int getTableRowId() {
        return tableRowId;
    }
    public void setTableRowId(int tableRowId) {
        this.tableRowId = tableRowId;
    }

    public long getGameLogDate() {
        return gameLogDate;
    }
    public void setGameLogDate(long gameLogDate) {
        this.gameLogDate = gameLogDate;
    }

    public boolean isWin() {
        return win;
    }
    public void setWin(boolean win) {
        this.win = win;
    }

    public int getExtraTry() {
        return extraTry;
    }
    public void setExtraTry(int nbTry) {
        this.extraTry = extraTry;
    }
}