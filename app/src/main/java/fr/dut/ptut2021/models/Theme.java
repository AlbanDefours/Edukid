package fr.dut.ptut2021.models;

public class Theme {
    private String name;
    private int image;

    public enum AllTheme {
        LETTRES, CHIFFRES;
    }

    //Constructor
    public Theme(String name, int image) {
        this.name = name;
        this.image = image;
    }

    //Getter
    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }

    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(int image) {
        this.image = image;
    }
}
