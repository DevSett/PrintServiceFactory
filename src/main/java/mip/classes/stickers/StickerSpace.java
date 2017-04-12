package mip.classes.stickers;

import mip.interfaces.StickerObject;

import java.awt.*;

/**
 * Created by killsett on 05.04.17.
 */
public class StickerSpace implements StickerObject {
    int sizeSpace;

    public StickerSpace(int space) {
        sizeSpace = space;
    }

    @Override
    public void print(Graphics2D graphics2D) {
        //do not use
    }

    @Override
    public int getLineHeight(Graphics graphics) {
        return sizeSpace;
    }
}
