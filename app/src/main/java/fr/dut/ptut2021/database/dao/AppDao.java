package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.ThemeGameCrossRef;
import fr.dut.ptut2021.models.User;

@Dao
public interface AppDao {

    //ThemeGameCrossRef
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertThemeGame(ThemeGameCrossRef themeGame);

    @Query("SELECT * FROM ThemeGameCrossRef")
    List<ThemeGameCrossRef> getAllThemeGame();

    default boolean tabThemeGameIsEmpty() {
        return getAllThemeGame().isEmpty();
    }

    //Request between Game and ThemeGameCrossRef
    @Query("SELECT Game.* FROM Game NATURAL JOIN ThemeGameCrossRef WHERE themeName = :themeName")
    List<Game> getAllGamesByTheme(String themeName);


    //ThemeDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTheme(Theme theme);

    @Query("SELECT * FROM Theme")
    List<Theme> getAllThemes();

    @Query("SELECT * FROM Theme WHERE themeId = :themeId")
    Theme getThemeById(int themeId);

    @Query("SELECT * FROM Theme WHERE themeName = :themeName")
    Theme getThemeByName(String themeName);

    @Update
    int updateTheme(Theme theme);

    @Query("DELETE FROM Theme WHERE themeId = :themeId")
    int deleteThemeById(int themeId);

    @Query("DELETE FROM Theme")
    int deleteAllThemes();

    default boolean tabThemeIsEmpty() {
        return getAllThemes().isEmpty();
    }


    //GameDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGame(Game game);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE gameId = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT * FROM Game WHERE gameName = :gameName")
    Game getGameByName(String gameName);

    @Update
    int updateGame(Game game);

    @Query("DELETE FROM Game WHERE gameId = :gameId")
    int deleteGameById(int gameId);

    @Query("DELETE FROM Game")
    int deleteAllGames();

    default boolean tabGameIsEmpty() {
        return getAllGames().isEmpty();
    }


    //UserDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM User WHERE userName = :userName")
    User getUserByName(String userName);

    @Update
    int updateUser(User user);

    @Query("DELETE FROM User WHERE userId = :userId")
    int deleteUserById(int userId);

    @Query("DELETE FROM User")
    int deleteAllUsers();

    default boolean tabUserIsEmpty() {
        return getAllUsers().isEmpty();
    }
}
