package mip.classes.helpers;

/**
 * Created by killsett on 05.04.17.
 */
public class VtoInt {
    private VtoInt() {
        throw new IllegalAccessError("VtoInt");
    }

    public static int parse(Object object) {
        return Integer.parseInt(String.valueOf(object));
    }
}
