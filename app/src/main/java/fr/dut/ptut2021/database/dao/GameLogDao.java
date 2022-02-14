package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.log.GameLog;

import java.util.List;

@Dao
public interface GameLogDao {

    //GAME LOG
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGameLog(GameLog gameLog);

    @Query("SELECT * FROM GameLog WHERE userId = :userId")
    List<GameLog> getAllGameLogByUserId(int userId);

    @Query("SELECT * FROM GameLog WHERE userId = :userId ORDER BY endGameDate DESC LIMIT 60")
    List<GameLog> getAllGameLogByUserLimit(int userId);

    @Query("SELECT l.* FROM GameLog  AS l NATURAL JOIN Game AS g WHERE l.userId = :userId AND g.themeName LIKE :themeName ORDER BY endGameDate DESC LIMIT 60")
    List<GameLog> getAllGameLogByUserLimitByTheme(int userId, String themeName);

    @Query("SELECT * FROM GameLog WHERE userId = :userId AND endGameDate >= :minTime")
    List<GameLog> getAllGameLogAfterTime(int userId, long minTime);

    @Query("SELECT l.* FROM GameLog as l NATURAL JOIN Game AS g WHERE l.userId = :userId AND g.themeName LIKE :themeName AND l.endGameDate >= :minTime")
    List<GameLog> getAllGameLogAfterTimeByTheme(int userId, String themeName, long minTime);

    @Query("SELECT * FROM GameLog WHERE userId = :userId AND gameId = :gameId")
    List<GameLog> getAllGameLogByGame(int userId, int gameId);

    @Query("SELECT count(*) FROM GameLog WHERE userId = :userId AND gameId = :gameId")
    int getGameLogNbGame(int userId, int gameId);

    @Query("SELECT stars FROM GameLog WHERE userId = :userId AND gameId = :gameId ORDER BY endGameDate DESC LIMIT :limit")
    List<Integer> getAllGameLogStarsLimit(int userId, int gameId, int limit);

    @Query("SELECT avg(stars) FROM GameLog WHERE userId = :userId")
    float getGameLogAvgStars(int userId);

    @Query("SELECT count(*) FROM GameLog WHERE userId = :userId")
    int getGameLogNb(int userId);

    @Query("SELECT max(difficulty) FROM GameLog WHERE userId = :userId AND gameId = :gameId")
    int getGameLogMaxDifByGame(int userId, int gameId);

    @Query("SELECT * FROM GameLog WHERE userId = :userId AND gameId = :gameId AND subGameId = :subGameId")
    List<GameLog> getAllGameLogBySubGame(int userId, int gameId, int subGameId);

    @Query("SELECT g.* FROM Game AS g NATURAL JOIN GameLog AS l WHERE l.userId = :userId AND g.gameId = l.gameId AND g.themeName LIKE :themeName GROUP BY g.gameId")
    List<Game> getAllGamePlayed(int userId, String themeName);

    @Query("SELECT s.* FROM SubGame AS s NATURAL JOIN GameLog AS l WHERE l.userId = :userId AND l.gameId = :gameId AND s.gameId = l.gameId GROUP BY s.subGameId")
    List<SubGame> getAllSubGamePlayed(int userId, int gameId);

    @Query("SELECT avg(stars) FROM GameLog WHERE userId = :userId AND gameId = :gameId AND difficulty = :difficulty")
    Float getGameAvgByGameIdAndDifficulty(int userId, int gameId, int difficulty);

    @Query("SELECT avg(l.stars) FROM GameLog AS l NATURAL JOIN SubGame AS s WHERE l.userId = :userId AND l.gameId = :gameId AND l.gameId = s.gameId AND l.subGameId = :subGameId AND l.difficulty = :difficulty")
    Float getGameAvgBySubGameIdAndDifficulty(int userId, int gameId, int subGameId, int difficulty);

    default boolean tabGameLogIsEmpty(int userId) {
        return getAllGameLogByUserId(userId).isEmpty();
    }

}
