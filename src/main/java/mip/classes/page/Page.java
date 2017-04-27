package mip.classes.page;

import mip.abstractClasses.AbstractPage;
import mip.classes.configurations.Sticker;
import mip.enums.Orienatation;
import mip.interfaces.StickerObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by killsett on 04.04.17.
 */
public class Page extends AbstractPage {
    private List<StickerObject>[] stickers;

    /**
     * @param stickers - элементы всех стикеров
     */
    protected Page(List<StickerObject>... stickers) {
        this.stickers = stickers;
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (stickers == null || getConfigurator() == null) {
            return NO_SUCH_PAGE;
        }


        int width = getConfigurator().getRow().getStartStickerSizePx();

        // time constructions
        for (Sticker sticker : getConfigurator().getStickers()) {
            if (Orienatation.LANDSCAPE.name().toLowerCase().equals(sticker.getOrientation())) {
                PageRotate pageRotate = new PageRotate(stickers);
                pageRotate.setConfigurator(getConfigurator());
                pageRotate.print(graphics, pageFormat, pageIndex);
                return PAGE_EXISTS;
            }
        }
        //
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
                    width += (previousSticker.getSize().getWidth());
                else
                    width += previousSticker.getSize().getWidth();
            }

            if (Orienatation.LANDSCAPE.name().toLowerCase().equals(currentSticker.getOrientation())) {
                getStickerImage(
                        graphics,
                        stickers[indexSticker],
                        currentSticker,
                        width,
                        true
                );
            } else {
                getStickerImage(
                        graphics,
                        stickers[indexSticker],
                        currentSticker,
                        width,
                        false
                );
            }


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
    private static Graphics getStickerImage(Graphics graphics, List<StickerObject> stickerObjects, Sticker sticker, int width, boolean rotate) {

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setFont(graphics.getFont());


        int sizeYLine = sticker.getPadding().getUp();
        int currentSize = sticker.getSize().getWidth();
        for (int indexLine = 0; indexLine < stickerObjects.size(); indexLine++) {
            StickerObject stickerObject = stickerObjects.get(indexLine);

            Graphics newSub;
            if (!rotate) {
                if (indexLine != 0) {
                    sizeYLine += stickerObject.getLineHeight(graphics);
                }
                newSub = graphics2D.create(
                        sticker.getPadding().getLeft() + width,
                        sizeYLine,
                        sticker.getSize().getWidth(),
                        stickerObject.getLineHeight(graphics) * 2
                );
            } else {
                //
                newSub = graphics2D.create(
                        width + currentSize,
                        0,
                        stickerObject.getLineHeight(graphics),
                        sticker.getSize().getHeight()
                );
                currentSize -= stickerObject.getLineHeight(graphics);
                newSub.setFont(newSub.getFont().deriveFont(new AffineTransform(AffineTransform.getRotateInstance(Math.toRadians(90)))));

                //Не рабочий вариант поворота
            }
            stickerObject.print((Graphics2D) newSub);
        }

        return graphics2D;
    }

//    /**
//     * Разворот изображения на 90 градусов по часовой
//     *
//     * @param img - изображение которое будет перерисованно
//     * @return новое повернутое изображение
//     */
//    private static BufferedImage rotateCw(BufferedImage img) {
//        int width = img.getWidth();
//        int height = img.getHeight();
//        BufferedImage newImage = new BufferedImage(height, width, img.getType());
//
//        for (int i = 0; i < width; i++)
//            for (int j = 0; j < height; j++)
//                newImage.setRGB(height - 1 - j, i, img.getRGB(i, j));
//
//        return newImage;
//    }


}

