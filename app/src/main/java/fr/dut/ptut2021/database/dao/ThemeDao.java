package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.Theme;

@Dao
public interface ThemeDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long createTheme(Theme theme);

    @Query("SELECT * FROM Theme")
    List<Theme> getAllThemes();

    @Query("SELECT * FROM Theme WHERE id = :themeId")
    Theme getTheme(int themeId);

    @Update
    int updateTheme(Theme theme);

    @Query("DELETE FROM Theme WHERE id = :themeId")
    int deleteTheme(int themeId);

    @Query("DELETE FROM Theme")
    int deleteAllThemes();

    default boolean tabThemeIsEmpty() {
        return getAllThemes().isEmpty();
    }

}