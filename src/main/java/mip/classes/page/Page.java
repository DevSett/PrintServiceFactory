package mip.classes.page;

import mip.classes.configurations.Configuration;
import mip.classes.configurations.Sticker;
import mip.interfaces.StickerObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by killsett on 04.04.17.
 */
public class Page implements Printable {
    private Configuration configuration;
    private List<StickerObject>[] stickers;

    /**
     * @param stickers - элементы всех стикеров
     */
    protected Page(List<StickerObject>... stickers) {
        this.stickers = stickers;
    }

    /**
     * Установка кофигуратора строки и стекров на строке
     *
     * @param configuration - конфигуратор
     */
    public void setConfigurator(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfigurator() {
        return configuration;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (stickers == null || configuration == null) {
            return NO_SUCH_PAGE;
        }
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        int width = configuration.getRow().getStartStickerSizePx();

        for (int indexSticker = 0; indexSticker < stickers.length; indexSticker++) {

            Sticker currentSticker = configuration.getStickers().get(indexSticker);

            Font font = new Font(
                    currentSticker.getFontCustom().getFont(),
                    Font.PLAIN,
                    currentSticker.getFontCustom().getFontSize()
            );

            graphics.setFont(font);

            if (indexSticker != 0) {
                Sticker previousSticker = configuration.getStickers().get(indexSticker - 1);
                if ("landscape".equals(previousSticker.getOrientation()))
                    width += previousSticker.getSize().getHeight();
                else
                    width += previousSticker.getSize().getWidth();
            }


            BufferedImage bufferedImage;

            if ("landscape".equals(currentSticker.getOrientation())) {
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
            width += configuration.getRow().getTabSizePx();

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

    /**
     * @return Количество стикеров в строке
     */
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

