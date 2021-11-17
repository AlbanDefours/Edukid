package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int image;
    private long creationDate;
    //java.sql.Date date=new java.sql.Date(creationDate);  TO CONVERT

    //Constructor
    public User(String name, int image) {
        this.name = name;
        this.image = image;
        this.creationDate = System.currentTimeMillis();
    }

    //Getter
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }
    public long getCreationDate() {
        return creationDate;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
