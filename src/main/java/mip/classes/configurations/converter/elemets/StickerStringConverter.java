package mip.classes.configurations.converter.elemets;

/**
 * Created by killsett on 12.04.17.
 */
public class StickerStringConverter implements StickerObjectConverter {
    private String string;
    private String field;

    public StickerStringConverter(String string, String field) {
        this.string = string;
        this.field = field;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
