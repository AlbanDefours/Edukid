package fr.dut.ptut2021.models;

public class MemoryCardLettre extends MemoryCard{

    private int font;

    public MemoryCardLettre(String value, int font) {
        super(value);
        this.font = font;
    }

    public MemoryCardLettre(MemoryCardLettre memoryCardlettre) {
        super(memoryCardlettre.getValue());
        this.font = memoryCardlettre.getFont();
    }

    @Override
    public int getDrawableImage() {
        return 0;
    }

    @Override
    public int getFont() {
        return font;
    }

    @Override
    public void setDrawableImage(int drawableImage) {

    }

    @Override
    public void setFont(int font) {
        this.font=font;
    }
}
