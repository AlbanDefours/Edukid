package fr.dut.ptut2021.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.dut.ptut2021.models.User;

@Dao
public interface UserDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long createUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id = :userId")
    User getUser(int userId);

    @Update
    int updateUser(User user);

    @Query("DELETE FROM User WHERE id = :userId")
    int deleteUser(int userId);

    @Query("DELETE FROM User")
    int deleteAllUsers();

    default boolean dbIsEmpty() {
        return getAllUsers().isEmpty();
    }

}