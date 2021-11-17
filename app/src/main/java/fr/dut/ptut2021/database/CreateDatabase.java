package fr.dut.ptut2021.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.dut.ptut2021.database.dao.UserDao;
import fr.dut.ptut2021.models.User;

@Database(entities = { User.class }, version = 2, exportSchema = true)
public abstract class CreateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CreateDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();

    // --- INSTANCE ---
    public static CreateDatabase getInstance(Context context) {

        //if (INSTANCE == null) {
            //synchronized (CreateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            CreateDatabase.class, "database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            //}
        //}
        return INSTANCE;
    }
}