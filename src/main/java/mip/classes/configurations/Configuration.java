package mip.classes.configurations;

import mip.classes.jsons.JSONParserConfiguration;
import mip.enums.Error;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Created by killsett on 05.04.17.
 */
public class Configuration {
    private List<Sticker> stickers;
    private Row row;
    private List<Converter> converters;
    private String path;

    public Configuration(String path) {
        this.path = path;
        parse();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Error parse() {
        try {
            JSONParserConfiguration parserConfiguration = new JSONParserConfiguration(path);
            parserConfiguration.parse();

            stickers = parserConfiguration.getStickers();
            row = parserConfiguration.getRow();
            converters = parserConfiguration.getConverters();

            return Error.OK;
        } catch (IOException e) {
            e.printStackTrace();
            return Error.ERROR_COMPILING;
        } catch (ParseException e) {
            e.printStackTrace();
            return Error.DONT_PARSE;
        }
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
}
