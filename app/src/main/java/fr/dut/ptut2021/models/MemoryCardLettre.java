package fr.dut.ptut2021.models;

public class MemoryCardLettre extends MemoryCard{

    private String font;

    public MemoryCardLettre(String value, String font) {
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
    public String getFont() {
        return font;
    }

    @Override
    public void setDrawableImage(int drawableImage) {

    }

    @Override
    public void setFont(String font) {
        this.font=font;
    }
}
