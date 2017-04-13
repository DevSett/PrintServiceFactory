package mip.classes.page;

import mip.abstractClasses.AbstractPage;
import mip.classes.configurations.Sticker;
import mip.enums.Orienatation;
import mip.interfaces.StickerObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by killsett on 04.04.17.
 */
public class PageRotate extends AbstractPage {
    private List<StickerObject>[] stickers;

    /**
     * @param stickers - элементы всех стикеров
     */
    protected PageRotate(List<StickerObject>... stickers) {
        this.stickers = stickers;
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (stickers == null || getConfigurator() == null) {
            return NO_SUCH_PAGE;
        }
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        int width = getConfigurator().getRow().getStartStickerSizePx();

        for (int indexSticker = 0; indexSticker < stickers.length; indexSticker++) {

            Sticker currentSticker = getConfigurator().getStickers().get(indexSticker);

            Font font = new Font(
                    currentSticker.getFontCustom().getFont(),
                    Font.PLAIN,
                    currentSticker.getFontCustom().getFontSize()
            );

            graphics.setFont(font);

            if (indexSticker != 0) {
                Sticker previousSticker = getConfigurator().getStickers().get(indexSticker - 1);
                if (Orienatation.LANDSCAPE.name().toLowerCase().equals(previousSticker.getOrientation()))
                    width += previousSticker.getSize().getHeight();
                else
                    width += previousSticker.getSize().getWidth();
            }


            BufferedImage bufferedImage;

            if (Orienatation.LANDSCAPE.name().toLowerCase().equals(currentSticker.getOrientation())) {
                bufferedImage = getStickerImage(
                        graphics,
                        stickers[indexSticker],
                        currentSticker,
                        true
                );
            } else {
                bufferedImage = getStickerImage(
                        graphics,
                        stickers[indexSticker],
                        currentSticker,
                        false
                );
            }


            graphics.drawImage(bufferedImage, width, 0, null);
            width += getConfigurator().getRow().getTabSizePx();

        }
        return PAGE_EXISTS;
    }

    /**
     * Отрисовка всех элиментов на стикере
     *
     * @param graphics
     * @param stickerObjects - элементы для отрисовки на стикере
     * @param sticker        - конфигурация данного стикера
     * @param rotate         - разворот стикера на 90 градусов
     * @return
     */
    private static BufferedImage getStickerImage(Graphics graphics, List<StickerObject> stickerObjects, Sticker sticker, boolean rotate) {
        BufferedImage bufferedImage =
                new BufferedImage(
                        sticker.getSize().getWidth(),
                        sticker.getSize().getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D graphicsImage = bufferedImage.createGraphics();
        graphicsImage.setFont(graphics.getFont());

        graphicsImage.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphicsImage.setColor(Color.BLACK);

        int sizeYLine = sticker.getPadding().getUp();
        for (int indexLine = 0; indexLine < stickerObjects.size(); indexLine++) {
            StickerObject stickerObject = stickerObjects.get(indexLine);
            if (indexLine != 0) {
                sizeYLine += stickerObject.getLineHeight(graphics);
            }
            Graphics newSub = graphicsImage.create(
                    sticker.getPadding().getLeft(),
                    sizeYLine
                           /* + sticker.getUpperBorder() * indexLine -  +отступы между строчками*/
                    ,
                    sticker.getSize().getWidth(),
                    stickerObject.getLineHeight(graphics)
            );

            stickerObject.print((Graphics2D) newSub);
        }

        return rotate ? rotateCw(bufferedImage) : bufferedImage;
    }

    /**
     * Разворот изображения на 90 градусов по часовой
     *
     * @param img - изображение которое будет перерисованно
     * @return новое повернутое изображение
     */
    private static BufferedImage rotateCw(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImage = new BufferedImage(height, width, img.getType());

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));

        return newImage;
    }

}
