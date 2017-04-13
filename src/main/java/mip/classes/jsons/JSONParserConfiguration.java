package mip.classes.jsons;

import mip.classes.configurations.Converter;
import mip.classes.configurations.Row;
import mip.classes.configurations.Sticker;
import mip.classes.configurations.converter.elemets.StickerObjectConverter;
import mip.classes.configurations.converter.elemets.StickerSpaceConverter;
import mip.classes.configurations.converter.elemets.StickerStringConverter;
import mip.classes.helpers.VtoInt;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by killsett on 04.04.17.
 */
public class JSONParserConfiguration {

    private String connector;
    private List<Sticker> stickers = new ArrayList<>();
    private Row row = new Row();
    private List<Converter> converters = new ArrayList<>();
    private String pathCategor;

    public JSONParserConfiguration(String nameConnector) {
        this.connector = nameConnector;
        this.pathCategor = connector.substring(0, connector.lastIndexOf("."));
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public void setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public void setConverters(List<Converter> converters) {
        this.converters = converters;
    }

    public void parse() throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(connector));

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray namesArray = (JSONArray) jsonObject.get("stickers");
        stickers(namesArray);

        String pathRow = (String) jsonObject.get("row");
        row(pathRow);

        JSONArray namesArray2 = (JSONArray) jsonObject.get("converters");
        converters(namesArray2);
    }

    private void converters(JSONArray arrays) throws IOException, ParseException {
        Iterator<String> iterator = arrays.iterator();
        List<String> names = new ArrayList<>();
        while (iterator.hasNext()) {
            names.add(iterator.next());
            converters.add(new Converter(names.get(names.size() - 1)));
        }

        for (String name : names) {
            JSONParser parserSticker = new JSONParser();
            Object object = parserSticker.parse(new FileReader(pathCategor + "/converters/" + name + ".json"));
            JSONObject jsonObjectSticker = (JSONObject) object;

            converters.forEach(converter -> {

                if (converter.getName().equals(name)) {

                    JSONArray jsonArrayStickerObjects = (JSONArray) jsonObjectSticker.get("stickerObjects");
                    List<StickerObjectConverter> list = new ArrayList<>();
                    if (jsonArrayStickerObjects != null)
                        jsonArrayStickerObjects.forEach(objectSticker -> {
                            JSONObject jsonBoss = (JSONObject) objectSticker;
                            List<Object> listObjects = new ArrayList<>();
                            List<Object> listFields = new ArrayList<>();

                            jsonBoss.forEach((jsonBossField, jsonBossObject) -> {

                                listObjects.add(jsonBossObject);
                                listFields.add(jsonBossField);
//                                if (jsonBossField != null)
//                                    if (jsonBossField.equals("stickerSpace")) {
//                                        list.add(new StickerSpaceConverter(VtoInt.parse(jsonBossObject.toString())));
//                                    } else {
//                                        if (!jsonBossField.toString().isEmpty())
//                                            list.add(new StickerStringConverter(jsonBossObject.toString(), jsonBossField.toString()));
//                                    }

                            });

                            Map map = null;
                            for (int index = 0; index < listFields.size(); index++) {
                                if ("template".equals(listFields.get(index))) {
                                    try {
                                        map = new TemplateParser(String.valueOf(listObjects.get(index))).parse();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (map != null)
                                for (int index = 0; index < listFields.size(); index++) {
                                    if ("type".equals(listFields.get(index))) {
                                        if (listObjects.get(index).equals("stickerString")) {
                                            list.add(
                                                    new StickerStringConverter(
                                                            String.valueOf(map.get("value")),
                                                            String.valueOf(map.get("field"))
                                                    ));
                                        } else {
                                            list.add(new StickerSpaceConverter(VtoInt.parse(map.get("value"))));
                                        }
                                    }
                                }

                           /* if (jsonBoss.get("stickerString") != null) {
                                JSONObject jsonObject = (JSONObject) jsonBoss.get("stickerString");
                                if (jsonObject != null) {
                                    String string = (String) jsonObject.get("string");
                                    String nameField = (String) jsonObject.get("name");
                                    if (nameField != null && !nameField.isEmpty())
                                        list.add(new StickerStringConverter(string, nameField));
                                }
                            }
                            if (jsonBoss.get("stickerSpace") != null) {
                                JSONObject jsonObject = (JSONObject) jsonBoss.get("stickerSpace");
                                if (jsonObject != null) {
                                    int space = VtoInt.parse(jsonObject.get("count"));
                                    list.add(new StickerSpaceConverter(space));
                                }
                            }
                            if (jsonBoss.get("stickerImage") != null) {
                                JSONObject jsonObject = (JSONObject) jsonBoss.get("stickerImage");
                                if (jsonObject != null) {
                                    //don't work
                                }
                            }*/
                        });
                    converter.setObjectFields(list);
                }
            });
        }

    }

    private void row(String path) throws IOException, ParseException {
        JSONParser parserRow = new JSONParser();
        Object object = parserRow.parse(new FileReader(pathCategor + "/" + path + ".json"));
        JSONObject jsonObjectRow = (JSONObject) object;

        row.setCountSticker(VtoInt.parse(jsonObjectRow.get("count")));
        row.setStartStickerSizePx(VtoInt.parse(jsonObjectRow.get("startStickerSizePx")));
        row.setTabSizePx(VtoInt.parse(jsonObjectRow.get("tabSizePx")));
    }

    private void stickers(JSONArray namesArray) throws IOException, ParseException {
        Iterator<String> iterator = namesArray.iterator();
        List<String> names = new ArrayList<>();

        while (iterator.hasNext()) {
            names.add(iterator.next());
            stickers.add(new Sticker(names.get(names.size() - 1)));
        }

        for (String name : names) {
            JSONParser parserSticker = new JSONParser();
            Object object = parserSticker.parse(new FileReader(pathCategor + "/stickers/" + name + ".json"));
            JSONObject jsonObjectSticker = (JSONObject) object;

            stickers.forEach(sticker -> {

                if (sticker.getName().equals(name)) {

                    JSONObject jsonObjectSize = (JSONObject) jsonObjectSticker.get("size");
                    sticker.getSize().setWidth(VtoInt.parse(jsonObjectSize.get("width")));
                    sticker.getSize().setHeight(VtoInt.parse(jsonObjectSize.get("height")));

                    JSONObject jsonObjectPadding = (JSONObject) jsonObjectSticker.get("padding");
                    sticker.getPadding().setUp(VtoInt.parse(jsonObjectPadding.get("up")));
                    sticker.getPadding().setLeft(VtoInt.parse(jsonObjectPadding.get("left")));

                    JSONObject jsonObjectFont = (JSONObject) jsonObjectSticker.get("fontCustom");
                    sticker.getFontCustom().setFont((String) jsonObjectFont.get("font"));
                    sticker.getFontCustom().setFontSize(VtoInt.parse(jsonObjectFont.get("fontSize")));
                    sticker.setOrientation(String.valueOf(jsonObjectSticker.get("orientation")));
                }
            });
        }
    }

}
