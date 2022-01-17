package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.databse.Game;
import fr.dut.ptut2021.models.databse.Theme;
import fr.dut.ptut2021.models.databse.ThemeGameCrossRef;
import fr.dut.ptut2021.models.databse.User;
import fr.dut.ptut2021.models.databse.Word;

@Dao
public interface AppDao {

    //ThemeGameCrossRef
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertThemeGame(ThemeGameCrossRef themeGame);

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
    void insertTheme(Theme theme);

    @Query("SELECT * FROM Theme")
    List<Theme> getAllThemes();

    @Query("SELECT * FROM Theme WHERE themeId = :themeId")
    Theme getThemeById(int themeId);

    @Query("SELECT * FROM Theme WHERE themeName = :themeName")
    Theme getThemeByName(String themeName);

    @Update
    void updateTheme(Theme theme);

    @Query("DELETE FROM Theme WHERE themeId = :themeId")
    void deleteThemeById(int themeId);

    @Query("DELETE FROM Theme")
    void deleteAllThemes();

    default boolean tabThemeIsEmpty() {
        return getAllThemes().isEmpty();
    }


    //GameDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(Game game);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE gameId = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT * FROM Game WHERE gameName = :gameName")
    Game getGameByName(String gameName);

    @Update
    void updateGame(Game game);

    @Query("DELETE FROM Game WHERE gameId = :gameId")
    void deleteGameById(int gameId);

    @Query("DELETE FROM Game")
    void deleteAllGames();

    default boolean tabGameIsEmpty() {
        return getAllGames().isEmpty();
    }


    //UserDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM User WHERE userName = :userName")
    User getUserByName(String userName);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM User WHERE userId = :userId")
    void deleteUserById(int userId);

    @Query("DELETE FROM User")
    void deleteAllUsers();

    default boolean tabUserIsEmpty() {
        return getAllUsers().isEmpty();
    }


    //Word
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Word word);

    @Query("SELECT * FROM Word")
    List<Word> getAllWords();

    @Query("SELECT * FROM Word WHERE word LIKE :str")
    Word getWordsIfContain(String str);

    default boolean tabWordIsEmpty() {
        return getAllWords().isEmpty();
    }
}
