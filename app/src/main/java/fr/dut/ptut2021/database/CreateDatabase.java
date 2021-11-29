package fr.dut.ptut2021.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.dut.ptut2021.database.dao.AppDao;
import fr.dut.ptut2021.database.dao.GameDao;
import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;
import fr.dut.ptut2021.models.ThemeGameCrossRef;
import fr.dut.ptut2021.models.User;
import fr.dut.ptut2021.models.stats.GameLog;
import fr.dut.ptut2021.models.stats.game.WordWithHoleData;

@Database(entities = { User.class, Theme.class, Game.class, ThemeGameCrossRef.class, GameLog.class, WordWithHoleData.class}, version = 2, exportSchema = true)
public abstract class CreateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CreateDatabase INSTANCE;

    // --- DAO ---
    public abstract AppDao appDao();
    public abstract GameDao gameDao();

    // --- INSTANCE ---
    public static CreateDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    CreateDatabase.class, "database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}