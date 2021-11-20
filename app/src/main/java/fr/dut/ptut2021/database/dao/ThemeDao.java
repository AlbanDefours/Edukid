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

    @Query("SELECT * FROM Theme WHERE themeId = :themeId")
    Theme getThemeById(int themeId);

    @Query("SELECT * FROM Theme WHERE name = :themeName")
    Theme getThemeByName(String themeName);

    @Update
    int updateTheme(Theme theme);

    @Query("DELETE FROM Theme WHERE themeId = :themeId")
    int deleteTheme(int themeId);

    @Query("DELETE FROM Theme")
    int deleteAllThemes();

    default boolean tabThemeIsEmpty() {
        return getAllThemes().isEmpty();
    }
}