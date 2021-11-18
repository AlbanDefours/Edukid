package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.ThemeGame;

@Dao
public interface ThemeGameDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long addThemeGame(ThemeGame themeGame);

    @Query("SELECT * FROM ThemeGame")
    List<ThemeGame> getAllThemeGames();

    //Renvoie une List des themeId correspondant au gameId en paramètre
    @Query("SELECT themeId FROM ThemeGame WHERE gameId = :gameId")
    List<Integer> getThemesIdByGame(int gameId);

    //Renvoie une List des gameId correspondant au themeId en paramètre
    @Query("SELECT gameId FROM ThemeGame WHERE themeId = :themeId")
    List<Integer> getGamesIdByTheme(int themeId);

    //Renvoie List Theme en fonction d'un game id
    @Query("SELECT Theme.* FROM Theme INNER JOIN ThemeGame INNER JOIN Game WHERE gameId = :gameId")
    List<Theme> getAllThemeByGameId(int gameId);

    //Renvoie List Game en fonction d'un theme id
    @Query("SELECT Game.* FROM Theme INNER JOIN ThemeGame INNER JOIN Game WHERE themeId = :themeId")
    //@Query("SELECT g.* FROM Theme as t INNER JOIN ThemeGame as tg ON t.id = tg.themeId INNER JOIN Game as g ON tg.gameId=g.id WHERE themeId = :themeId")
    List<Game> getAllGameByThemeId(int themeId);

    @Update
    int updateThemeGame(ThemeGame themeGame);

    @Query("DELETE FROM ThemeGame WHERE gameId = :gameId")
    int deleteThemeGameByGame(int gameId);

    @Query("DELETE FROM ThemeGame WHERE themeId = :themeId")
    int deleteThemeGameByTheme(int themeId);

    @Query("DELETE FROM ThemeGame")
    int deleteAllThemeGames();

}