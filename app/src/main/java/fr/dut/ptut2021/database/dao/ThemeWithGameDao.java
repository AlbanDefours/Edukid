package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.dut.ptut2021.models.ThemeWithGame;

@Dao
public interface ThemeWithGameDao {

    @Transaction
    @Query("SELECT * FROM Game")
    public List<ThemeWithGame> getSongsWithPlaylists();

}
