package mip.classes.stickers;

import mip.interfaces.StickerObject;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Don't working
 */
public class StickerImage implements StickerObject {


    BufferedImage image;


    public StickerImage(BufferedImage image, int width, int height) {
        setImage(image, width, height);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image, int width, int height) {
        this.image = image;
    }

    @Override
    public void print(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, getLineHeight(graphics2D), null);
    }


    @Override
    public int getLineHeight(Graphics graphics) {
        return image.getHeight();
    }
}
