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
    private int gameId;
    private long gameLogDate;
    private boolean win;
    private int nbTry;

    public GameLog(String gameName, int gameId, boolean win, int nbTry) {
        this.gameName = gameName;
        this.gameId = gameId;
        this.gameLogDate = System.currentTimeMillis();
        //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT
        this.win = win;
        this.nbTry = nbTry;
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

    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public int getNbTry() {
        return nbTry;
    }
    public void setNbTry(int nbTry) {
        this.nbTry = nbTry;
    }
}