package fr.dut.ptut2021.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int image;

    //Constructor
    public User(String name, int image) {
        this.name = name;
        this.image = image;
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

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public void setImageProfile(int image) {
        this.image = image;
    }

}
