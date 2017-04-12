package mip.classes.page;

import mip.classes.configurations.Configuration;
import mip.interfaces.StickerObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Created by killsett on 04.04.17.
 */
public class FabricPage {
    private Configuration configuration;

    public FabricPage(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * создание новой строки стикеров
     *
     * @param stickers - стрикеры строки
     * @return строку стикеров
     * @throws IOException
     * @throws ParseException
     */
    public Page getPage(List<StickerObject>... stickers){
        Page page = new Page(stickers);
        page.setConfigurator(configuration);
        return page;
    }

    /**
     * создание новых строк стикеров
     *
     * @param stickers - все стикеры
     * @return строки стикеров
     */
    public Page[] getPages(List<StickerObject>... stickers) {

        int currentSticker = 0;
        int countStickers = configuration.getRow().getCountSticker();
        int pageLength = (int) Math.ceil(((double) stickers.length) / ((double) countStickers));

        Page[] pages = new Page[pageLength];

        for (int indexPage = 0; indexPage < pageLength; indexPage++) {

            if (indexPage + 1 == pageLength) {
                countStickers = stickers.length - currentSticker;
            }

            List<StickerObject>[] stickersToPage = new List[countStickers];

            for (int indexSticker = 0; indexSticker < countStickers; indexSticker++) {
                stickersToPage[indexSticker] = stickers[currentSticker++];
            }

            pages[indexPage] = getPage(stickersToPage);
        }


        return pages;
    }
}
