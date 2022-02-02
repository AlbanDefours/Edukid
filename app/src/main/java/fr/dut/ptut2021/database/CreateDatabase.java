package fr.dut.ptut2021.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.dut.ptut2021.database.dao.AppDao;
import fr.dut.ptut2021.database.dao.GameDao;
import fr.dut.ptut2021.database.dao.GameLogDao;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.models.database.game.MemoryData;
import fr.dut.ptut2021.models.database.app.Word;
import fr.dut.ptut2021.models.database.game.MemoryDataCardCrossRef;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.models.database.game.DrawOnItData;
import fr.dut.ptut2021.models.database.game.PlayWithSoundData;
import fr.dut.ptut2021.models.database.game.WordWithHoleData;

@Database(entities = {
        User.class,
        Theme.class,
        Game.class,
        SubGame.class,
        Word.class,
        Card.class,
        GameLog.class,
        WordWithHoleData.class,
        PlayWithSoundData.class,
        DrawOnItData.class,
        MemoryData.class,
        MemoryDataCardCrossRef.class
}, version = 9, exportSchema = false)


public abstract class CreateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CreateDatabase INSTANCE;

    // --- DAO ---
    public abstract AppDao appDao();
    public abstract GameDao gameDao();
    public abstract GameLogDao gameLogDao();

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