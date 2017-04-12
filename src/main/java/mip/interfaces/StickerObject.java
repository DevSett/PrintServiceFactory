package mip.interfaces;

import java.awt.*;

public interface StickerObject{
    void print(Graphics2D graphics2D);

    /**
     * Высота линии
     * @param graphics
     * @return
     */
    int getLineHeight(Graphics graphics);
}
