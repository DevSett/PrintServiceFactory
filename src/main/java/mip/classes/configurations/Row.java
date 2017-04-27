package mip.classes.configurations;

/**
 * Created by killsett on 04.04.17.
 */
public class Row {
    private String path;
    private int countSticker;
    private int tabSizePx;
    private int startStickerSizePx;

    public int getCountSticker() {
        return countSticker;
    }

    public void setCountSticker(int countSticker) {
        this.countSticker = countSticker;
    }

    public int getTabSizePx() {
        return tabSizePx;
    }

    public void setTabSizePx(int tabSizePx) {
        this.tabSizePx = (int) (tabSizePx * 2.8d);
    }

    public int getStartStickerSizePx() {
        return startStickerSizePx;
    }

    public void setStartStickerSizePx(int startStickerSizePx) {
        this.startStickerSizePx = (int) (startStickerSizePx * 2.8d);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
