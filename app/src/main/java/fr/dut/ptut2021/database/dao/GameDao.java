package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.dut.ptut2021.models.stats.GameLog;
import fr.dut.ptut2021.models.stats.game.WordWithHoleData;

@Dao
public interface GameDao {

    //WordWithHoleStats
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertWWHStats(WordWithHoleData wordWithHoleStats);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId")
    List<WordWithHoleData> getAllWWHStats(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND word = :word")
    WordWithHoleData getWWHByWord(int userId, String word);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND syllable = :syllable")
    List<WordWithHoleData> getWWHBySyllable(int userId, String syllable);

    @Query("SELECT syllable FROM WordWithHoleData WHERE userId = :userId")
    List<String> getAllSyllable(int userId);

    @Query("SELECT image FROM WordWithHoleData WHERE userId = :userId")
    List<Integer> getAllImage(int userId);


    //GameLog
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGameLog(GameLog gameLog);

    @Query("SELECT * FROM GameLog")
    List<GameLog> getAllGameLog();

    //GameLog For WordWithHoleData
    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId")
    List<GameLog> getWWHLogByUser(int userId);

    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId AND g.gameName = :gameName")
    List<GameLog> getWWHLogByUserAndGame(int userId, String gameName);



}
