package fr.dut.ptut2021.models;

public abstract class MemoryCard {
    private String value;

    private boolean hidden=true;
    private int nbReturn=0;


    public MemoryCard(String value){
        this.value = value;
    }

    public MemoryCard(MemoryCard memoryCard){
        this.value = memoryCard.getValue();
    }

    //getter
    public boolean isHidden() {
        return hidden;
    }
    public abstract int getDrawableImage();
    public abstract int getFont();
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
    public abstract void setDrawableImage(int drawableImage);
    public abstract void setFont(int font);
    public void setValue(String value) {
        this.value = value;
    }

    public int getNbReturn() {
        return nbReturn;
    }
}


