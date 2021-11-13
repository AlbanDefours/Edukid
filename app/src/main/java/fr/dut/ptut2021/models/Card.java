package fr.dut.ptut2021.models;

public class Card {
    private String value;
    private boolean hidden=true;
    private String nameImage;


    public Card(String value,String nameImage){
        this.value = value;
        this.nameImage = nameImage;
    }

    public Card(Card card){
        this.value = card.getValue();
        this.nameImage = card.getNameImage();
    }

    //getter
    public boolean isHidden() {
        return hidden;
    }
    public String getNameImage() {
        return nameImage;
    }
    public String getValue() {
        return value;
    }

    //setter
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
