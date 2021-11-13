package fr.dut.ptut2021.models;

import android.graphics.drawable.Drawable;

public class User {
    private int id;
    private String name;
    private int image;

    //Constructor
    public User(int id, String name, int image) {
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
