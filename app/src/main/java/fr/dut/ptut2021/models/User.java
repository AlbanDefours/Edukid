package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String userName;
    private int userImage;
    private long userCreationDate = 0;
    //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT

    //Constructor
    public User(String userName, int userImage) {
        this.userName = userName;
        this.userImage = userImage;
        this.userCreationDate = System.currentTimeMillis();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public long getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(long userCreationDate) {
        this.userCreationDate = userCreationDate;
    }
}
