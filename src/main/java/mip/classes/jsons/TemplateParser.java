package mip.classes.jsons;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by killsett on 12.04.17.
 */
public class TemplateParser {
    private String text;
    private String value;
    private String fieldName;
    private Type type;

    public TemplateParser(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return hashmap -> keys: type - тип поля, value - значение, field - имя поля;
     */
    public Map parse() throws ClassNotFoundException {
        HashMap map = new HashMap();
        String[] keys = {"type", "value", "field"};

        value = text.substring(0, text.indexOf("->"));

        String tt = text.substring(text.lastIndexOf('{') + 1, text.indexOf('}'));

        map.put(keys[1], value);

        int indexPosSimbol = tt.indexOf(':');
        if (indexPosSimbol == -1) {
            fieldName = tt.substring(1, tt.length() - 1);
            map.put(keys[2], fieldName);
            //type - null
            return map;
        }
        fieldName = tt.substring(1, tt.indexOf(':') - 1);
        String fieldType = tt.substring(indexPosSimbol + 2, tt.length() - 1);
        type = Class.forName("java.lang." + fieldType);
        map.put(keys[2], fieldName);
        map.put(keys[0], type);
        return map;
    }
}
