package fr.dut.ptut2021.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import fr.dut.ptut2021.database.dao.UserDao;
import fr.dut.ptut2021.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class CreateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile CreateDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();

    // --- INSTANCE ---
    public static CreateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CreateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CreateDatabase.class, "Database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}