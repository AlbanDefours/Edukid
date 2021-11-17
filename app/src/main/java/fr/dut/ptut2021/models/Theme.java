package fr.dut.ptut2021.models;

import java.util.List;

public class Theme {
    private List<String> name;
    private List<Integer> image;

    //Constructor
    public Theme(String name, int image) {
        this.name.add(name);
        this.image.add(image);
    }

    //Getter
    public String getName(int pos) {
        return name.get(pos);
    }
    public int getImage(int pos) {
        return image.get(pos);
    }

    //Setter
    public void setName(String name) {
        this.name.add(name);
    }
    public void setImage(int image) {
        this.image.add(image);
    }
}
