package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.models.database.app.Word;

@Dao
public interface AppDao {

    //ThemeDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTheme(Theme theme);

    @Query("SELECT * FROM Theme")
    List<Theme> getAllThemes();

    @Query("SELECT * FROM Theme WHERE themeName = :themeName")
    Theme getThemeByName(String themeName);

    @Update
    void updateTheme(Theme theme);

    @Query("DELETE FROM Theme WHERE themeName = :themeName")
    void deleteThemeByName(int themeName);

    @Query("DELETE FROM Theme")
    void deleteAllThemes();


    //GameDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(Game game);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE themeName = :themeName")
    List<Game> getAllGamesByTheme(String themeName);

    @Query("SELECT * FROM Game WHERE gameId = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT gameId FROM Game WHERE gameName = :gameName AND themeName = :themeName")
    int getGameId(String gameName, String themeName);

    @Update
    void updateGame(Game game);

    @Query("DELETE FROM Game WHERE gameId = :gameId")
    void deleteGameById(int gameId);

    @Query("DELETE FROM Game")
    void deleteAllGames();


    //SubGame
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubGame(SubGame subGame);

    @Query("SELECT * FROM SubGame")
    List<SubGame> getAllSubGames();

    @Query("SELECT * FROM SubGame WHERE gameId = :gameId")
    List<SubGame> getAllSubGamesByGame(int gameId);

    @Query("SELECT * FROM SubGame WHERE subGameId = :subGameId")
    SubGame getSubGameById(int subGameId);

    @Query("SELECT * FROM SubGame WHERE subGameName = :subGameName")
    SubGame getSubGameByName(String subGameName);

    @Update
    void updateSubGame(SubGame subGame);

    @Query("DELETE FROM SubGame WHERE subGameId = :subGameId")
    void deleteSubGameById(int subGameId);

    @Query("DELETE FROM SubGame")
    void deleteAllSubGames();


    //UserDao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM User WHERE userName = :userName")
    User getUserByName(String userName);

    @Query("SELECT userImage FROM User WHERE userImageType != 0 ORDER BY userImage DESC LIMIT 1")
    String getUserImageMaxInt();

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

    @Query("SELECT COUNT(*) FROM Word")
    int getNbWords();

    @Query("SELECT * FROM Word WHERE word LIKE :str")
    Word getWordIfContain(String str);

}
