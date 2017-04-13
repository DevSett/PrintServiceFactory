package mip.abstractClasses;

import mip.classes.configurations.Configuration;
import mip.classes.configurations.Sticker;

import java.awt.print.Printable;

/**
 * Created by killsett on 13.04.17.
 */
public abstract class AbstractPage implements Printable {
    private Configuration configuration;

       public void setConfigurator(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfigurator() {
        return configuration;
    }

    public int getCountStickers() {
        return configuration.getRow().getCountSticker();
    }

    /**
     * @return magic
     */
    public int getWidth() {
        int width = configuration.getRow().getStartStickerSizePx();
        for (Sticker sticker : configuration.getStickers()) {
            width += sticker.getSize().getWidth();
            width += configuration.getRow().getTabSizePx();
        }
        return width;
    }

    /**
     * @return magic
     */
    public int getHeight() {
        int heightMax = 0;
        int upMax = 0;
        for (Sticker sticker : configuration.getStickers()) {
            if (heightMax < sticker.getSize().getHeight()) {
                heightMax = sticker.getSize().getHeight();
            }
            if (upMax < sticker.getPadding().getUp()) {
                upMax = sticker.getPadding().getUp();
            }

        }

        return heightMax + upMax;
    }
}
