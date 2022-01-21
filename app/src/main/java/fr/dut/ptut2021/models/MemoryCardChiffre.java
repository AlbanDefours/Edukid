package fr.dut.ptut2021.models;

import fr.dut.ptut2021.models.MemoryCard;

public class MemoryCardChiffre extends MemoryCard {

    private int drawableImage;

    public MemoryCardChiffre(String value, int drawableImage) {
        super(value);
        this.drawableImage = drawableImage;
    }

    public MemoryCardChiffre(MemoryCardChiffre memoryCardChiffre) {
        super(memoryCardChiffre.getValue());
        this.drawableImage = memoryCardChiffre.getDrawableImage();
    }

    @Override
    public int getDrawableImage() {
        return drawableImage;
    }

    @Override
    public String getFont() {
        return null;
    }

    @Override
    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    @Override
    public void setFont(String font) {

    }
}
