package mip.classes.stickers;

import mip.interfaces.StickerObject;

import java.awt.*;

/**
 * Created by killsett on 04.04.17.
 */
public class StickerString implements StickerObject {
    String line;
    Font font;

    public StickerString(String line, Font font) {
        this.line = line;
        this.font = font;
    }

    public StickerString(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }


    @Override
    public void print(Graphics2D graphics2D) {
        if (font != null) {
            graphics2D.setFont(font);
        }
        graphics2D.drawString(line, 0, getLineHeight(graphics2D));
    }

    @Override
    public int getLineHeight(Graphics graphics) {
        if (font != null) {
            return graphics.getFontMetrics(font).getHeight();
        } else {
            return graphics.getFontMetrics().getHeight();
        }
    }
}
