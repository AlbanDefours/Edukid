package fr.dut.ptut2021.models;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Theme {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Drawable image;

    //Constructor
    public Theme(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    //Getter
    public String getName() {
        return name;
    }
    public Drawable getImage() {
        return image;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }

}
