package fr.dut.ptut2021.models.database.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String userName;
    private String userImage;
    private int userImageType;
    private long userCreationDate = 0;
    //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT

    //Constructor
    public User(String userName, String userImage, int userImageType) {
        this.userName = userName;
        this.userImage = userImage;
        this.userImageType = userImageType;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public long getUserCreationDate() {
        return userCreationDate;
    }

    public int getUserImageType() {
        return userImageType;
    }

    public void setUserImageType(int userImageType) {
        this.userImageType = userImageType;
    }

    public void setUserCreationDate(long userCreationDate) {
        this.userCreationDate = userCreationDate;
    }
}
