package mip.classes.helpers;


import javafx.scene.control.TextField;

/**
 * Created by killsett on 19.04.17.
 */
public class TextFieldFormatter extends TextField {
    public static final String INT_MATCHES = "01234567890";
    public static final String FLOAT_MATCHES = INT_MATCHES + ".";

    public TextFieldFormatter(String name, String matches) {
        super(name);
        if (matches != null)
            textProperty().addListener((observableValue, oldValue, newValue) -> {
                if (!newValue.isEmpty())
                    if (matches.indexOf(newValue) != -1) {
                        setText(newValue);
                    } else {
                        try {
                            // тут можете парсить строку как захотите
                            if (matches.indexOf(".") == -1 || matches.indexOf(",") == -1)
                                setText(String.valueOf(Integer.parseInt(newValue)));
                            else setText(String.valueOf(Double.parseDouble(newValue)));
                        } catch (NumberFormatException e) {
                            setText(oldValue);
                        }
                    }
            });
    }

}
