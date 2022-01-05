package fr.dut.ptut2021.models;

public class Card {
    private String value;
    private boolean hidden = true;
    private int drawableImage;


    public Card(String value,int drawableImage){
        this.value = value;
        this.drawableImage = drawableImage;
    }

    public Card(Card card){
        this.value = card.getValue();
        this.drawableImage = card.getDrawableImage();
    }

    //getter
    public boolean isHidden() {
        return hidden;
    }
    public int getDrawableImage() {
        return drawableImage;
    }
    public String getValue() {
        return value;
    }

    //setter
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }
    public void setValue(String value) {
        this.value = value;
    }
}