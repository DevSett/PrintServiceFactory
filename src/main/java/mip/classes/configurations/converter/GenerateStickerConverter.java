package mip.classes.configurations.converter;

import mip.classes.configurations.Converter;
import mip.classes.configurations.converter.elemets.StickerObjectConverter;
import mip.classes.configurations.converter.elemets.StickerSpaceConverter;
import mip.classes.configurations.converter.elemets.StickerStringConverter;
import mip.classes.stickers.StickerSpace;
import mip.classes.stickers.StickerString;
import mip.interfaces.StickerObject;
import mip.interfaces.ToStickerConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by killsett on 12.04.17.
 */
public class GenerateStickerConverter<T> {
    public ToStickerConverter<T> create(Converter converter) {

        return val -> {
            List<StickerObject> stickerObjects = new ArrayList<>();
            for (StickerObjectConverter field : converter.getObjectFields()) {

                if (field instanceof StickerStringConverter) {
                    actionStickerString(val, stickerObjects, (StickerStringConverter) field);
                }

                if (field instanceof StickerSpaceConverter) {
                    stickerObjects.add(new StickerSpace(((StickerSpaceConverter) field).getSpace()));
                }

            }

            return stickerObjects;
        };

    }

    private void actionStickerString(T val, List<StickerObject> stickerObjects, StickerStringConverter field) throws IllegalAccessException, InvocationTargetException {
        StickerStringConverter stickerStringConverter = field;
        if (stickerStringConverter == null) {
            return;
        }
        for (Method method : val.getClass().getMethods()) {
            if (method.getName().equals("get" + stickerStringConverter.getField().substring(0, 1).toUpperCase() + stickerStringConverter.getField().substring(1))) {
                Object object = method.invoke(val, new Object[]{});
                if (object == null) {
                    continue;
                }
                stickerObjects.add(new StickerString(stickerStringConverter.getString() + object));
            }
        }
    }
}
