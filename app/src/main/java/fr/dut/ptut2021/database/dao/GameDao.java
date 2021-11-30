package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.stats.GameLog;
import fr.dut.ptut2021.models.stats.game.WordWithHoleData;

@Dao
public interface GameDao {

    //WordWithHoleData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertWWHData(WordWithHoleData wordWithHoleStats);

    @Update
    int updateWWHData(WordWithHoleData wordWithHoleStats);

    @Query("UPDATE WordWithHoleData SET lastUsed = 0 WHERE userId = :userId")
    int updateAllLastUsed(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND word = :word AND syllable = :syllable")
    WordWithHoleData getWWHData(int userId, String word, String syllable);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId")
    List<WordWithHoleData> getAllWWHData(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND lastUsed = 1")
    List<WordWithHoleData> getAllWWHDataLastUsed(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND lastUsed = 0")
    List<WordWithHoleData> getAllWWHDataNotLastUsed(int userId);


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
