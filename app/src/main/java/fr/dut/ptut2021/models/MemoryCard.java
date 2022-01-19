package fr.dut.ptut2021.models;

public class MemoryCard {
    private String value;

    private boolean hidden=true;
    private int nbReturn=0;
    private int drawableImage;


    public MemoryCard(String value, int drawableImage){
        this.value = value;
        this.drawableImage = drawableImage;
    }

    public MemoryCard(MemoryCard memoryCard){
        this.value = memoryCard.getValue();
        this.drawableImage = memoryCard.getDrawableImage();
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
        if(hidden){
            nbReturn++;
        }
        this.hidden = hidden;
    }
    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public int getNbReturn() {
        return nbReturn;
    }
}


