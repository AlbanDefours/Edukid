package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.ThemeWithGame;

@Dao
public interface GameDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long createGame(Game game);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE gameId = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT gameId FROM Game WHERE name = :gameName")
    int getIdByName(String gameName);

    @Update
    int updateGame(Game game);

    @Query("DELETE FROM Game")
    int deleteAllGames();

    default boolean tabGameIsEmpty() {
        return getAllGames().isEmpty();
    }
}