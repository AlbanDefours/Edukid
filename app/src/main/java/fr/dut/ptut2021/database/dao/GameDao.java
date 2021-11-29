package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.dut.ptut2021.models.stats.GameLog;
import fr.dut.ptut2021.models.stats.game.WordWithHoleStats;

@Dao
public interface GameDao {

    //WordWithHoleStats (WWH)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertWWHStats(WordWithHoleStats wordWithHoleStats);

    @Query("SELECT * FROM WordWithHoleStats")
    List<WordWithHoleStats> getAllWWHStats();

    @Query("SELECT * FROM WordWithHoleStats WHERE word = :word")
    List<WordWithHoleStats> getWWHByWord(String word);

    @Query("SELECT * FROM WordWithHoleStats WHERE syllable = :syllable")
    List<WordWithHoleStats> getWWHBySyllable(String syllable);

    //gameLog
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGameLog(GameLog gameLog);

    @Query("SELECT * FROM GameLog")
    List<GameLog> getAllGameLog();

    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleStats AS w WHERE w.userId = :userId")
    List<GameLog> getWWHLogByUser(int userId);

    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleStats AS w WHERE w.userId = :userId AND g.gameName = :gameName")
    List<GameLog> getWWHLogByUserAndGame(int userId, String gameName);



}
