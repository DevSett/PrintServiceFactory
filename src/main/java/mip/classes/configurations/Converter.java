package mip.classes.configurations;

import mip.classes.configurations.converter.elemets.StickerObjectConverter;

import java.util.List;

/**
 * Created by killsett on 12.04.17.
 */
public class Converter {
    private String pathName;
    private List<StickerObjectConverter> objectFields;

    public Converter(String pathName) {
        this.pathName = pathName;
    }

    public String getName() {
        return pathName;
    }

    public void setName(String pathName) {
        this.pathName = pathName;
    }

    public List<StickerObjectConverter> getObjectFields() {
        return objectFields;
    }

    public void setObjectFields(List<StickerObjectConverter> objectFields) {
        this.objectFields = objectFields;
    }
}
