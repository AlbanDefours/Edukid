package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Game;

@Dao
public interface GameDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long createGame(Game game);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE id = :gameId")
    Game getGame(int gameId);

    @Update
    int updateGame(Game game);


    @Query("DELETE FROM ThemeGame WHERE gameId = :gameId")
    int deleteGameInThemeGame(int gameId);

    @Query("DELETE FROM Game WHERE id = :gameId")
    int deleteGameInGame(int gameId);

    default void deleteTheme(int gameId) {
        deleteGameInThemeGame(gameId);
        deleteGameInGame(gameId);
    }


    @Query("DELETE FROM ThemeGame")
    int deleteAllGamesInThemeGame();

    @Query("DELETE FROM Game")
    int deleteAllGamesInGame();

    default void deleteAllThemes() {
        deleteAllGamesInThemeGame();
        deleteAllGamesInGame();
    }


    default boolean tabGameIsEmpty() {
        return getAllGames().isEmpty();
    }
}