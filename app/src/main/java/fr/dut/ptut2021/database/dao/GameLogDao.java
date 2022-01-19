package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.models.database.log.GameResultLog;

import java.util.List;

@Dao
public interface GameLogDao {

    //GAME RESULT LOG
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGameResultLog(GameResultLog gameResultLog);

    @Query("SELECT * FROM GameResultLog")
    List<GameResultLog> getAllGameResultLog();



    //GAME LOG
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGameLog(GameLog gameLog);

    @Query("SELECT * FROM GameLog")
    List<GameLog> getAllGameLog();

    //@Query("SELECT * FROM GameLog WHERE userId = :userId AND ")
    //List<GameLog> getGameLogByDate(int userId, long startDate);


    //For WordWithHoleData
    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId")
    List<GameLog> getWWHLogByUser(int userId);

    @Query("SELECT g.* FROM GameLog AS g NATURAL JOIN WordWithHoleData AS w WHERE w.userId = :userId AND g.gameName = :gameName")
    List<GameLog> getWWHLogByUserAndGame(int userId, String gameName);


}
