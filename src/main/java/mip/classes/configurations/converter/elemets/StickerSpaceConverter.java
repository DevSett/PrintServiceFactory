package mip.classes.configurations.converter.elemets;

/**
 * Created by killsett on 12.04.17.
 */
public class StickerSpaceConverter implements StickerObjectConverter{
    private int space;

    public StickerSpaceConverter(int space) {
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
