package mip.interfaces;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by killsett on 06.04.17.
 */
@FunctionalInterface
public interface ToStickerConverter<T> {
    public List<StickerObject> convert(T val) throws InvocationTargetException, IllegalAccessException, InstantiationException;

}
