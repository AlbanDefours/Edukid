package fr.dut.ptut2021.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import fr.dut.ptut2021.models.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE id = :userId")
    LiveData<User> getUsers(int userId);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    int createUser(User user);

    @Update
    int updateUser(User user);

    @Query("DELETE FROM User WHERE id = :userId")
    int deleteUser(int userId);

}
