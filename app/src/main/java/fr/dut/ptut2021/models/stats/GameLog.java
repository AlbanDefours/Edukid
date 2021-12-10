package fr.dut.ptut2021.models.stats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameLog {

    @PrimaryKey(autoGenerate = true)
    private int gameLogId;
    //Nom du jeu (de la table du jeu)
    private String gameName;
    //Id de la ligne correspondante (de la table gameName)
    private int tableRowId;
    private long gameLogDate;
    private boolean win;
    private int extraTry;

    public GameLog(String gameName, int tableRowId, boolean win, int extraTry) {
        this.gameName = gameName;
        this.tableRowId = tableRowId;
        this.gameLogDate = System.currentTimeMillis();
        //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT
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

    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
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