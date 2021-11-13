package fr.dut.ptut2021.models;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int id;
    private String name;
    private Drawable image;

    //Constructor
    public User(int id, String name, Drawable image) {
        this.id = id;
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
    public Drawable getImage() {
        return image;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public void setImageProfile(Drawable image) {
        this.image = image;
    }

}
