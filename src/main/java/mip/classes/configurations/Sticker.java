package mip.classes.configurations;

/**
 * Created by killsett on 04.04.17.
 */
public class Sticker {
    private String name;
    private String orientation;

    private Size size = new Size(0, 0);
    private FontCustom font = new FontCustom(10);
    private Padding padding = new Padding();

    public Sticker(String name) {
        this.name = name;
    }


    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public FontCustom getFontCustom() {
        return font;
    }

    public void setFontCustom(FontCustom font) {
        this.font = font;
    }

    public Padding getPadding() {
        return padding;
    }

    public void setPadding(Padding padding) {
        this.padding = padding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }


    public class Size {
        int width;
        int height;

        public Size(int width, int height) {
            this.width = (int) (width * 2.8d);
            this.height = (int) (height * 2.8d);
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = (int) (width * 2.8d);
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = (int) (height * 2.8d);
        }
    }

    public class Padding {
        int up;
        int left;

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }



    }

    public class FontCustom {
        int fontSize;
        String font;

        public FontCustom(int fontSize) {
            this.fontSize = fontSize;
        }

        public FontCustom(String font) {
            this.font = font;
        }

        public FontCustom(int fontSize, String font) {
            this.fontSize = fontSize;
            this.font = font;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }
    }

}
