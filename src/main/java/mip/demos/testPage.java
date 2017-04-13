package mip.demos;

import mip.classes.configurations.Configuration;
import mip.classes.page.FabricPage;
import mip.classes.page.Page;
import mip.classes.stickers.StickerSpace;
import mip.classes.stickers.StickerString;
import mip.interfaces.StickerObject;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by killsett on 04.04.17.
 */
public class testPage {
    public static void main(String[] args) throws PrinterException, IOException, ParseException {

        List<StickerObject>[] stickerObjects = new List[3];
        stickerObjects[0] = new ArrayList<>();
        stickerObjects[0].add(new StickerString("Иванов иванович"));
        stickerObjects[0].add(new StickerSpace(11));
        stickerObjects[0].add(new StickerString("Иванов иванович2"));
        stickerObjects[0].add(new StickerString("Иванов иванович3"));
        stickerObjects[0].add(new StickerString("Иванов иванович4"));
        stickerObjects[1] = new ArrayList<>();
        stickerObjects[1].add(new StickerString("Иванов иванович"));
        stickerObjects[1].add(new StickerString("Иванов иванович1"));
        stickerObjects[1].add(new StickerString("Иванов иванович2"));
        stickerObjects[1].add(new StickerString("Иванов иванович3"));
        stickerObjects[1].add(new StickerString("Иванов иванович4"));
        stickerObjects[2] = new ArrayList<>();
        stickerObjects[2].add(new StickerString("Иванов иванович"));
        stickerObjects[2].add(new StickerString("Иванов иванович1"));
        stickerObjects[2].add(new StickerString("Иванов иванович2"));
        stickerObjects[2].add(new StickerString("Иванов иванович3"));
        stickerObjects[2].add(new StickerString("Иванов иванович4"));

        Configuration configuration = new Configuration("Connector1.json");
        configuration.parse();

        Page page = new FabricPage(configuration).getPage(stickerObjects);
        BufferedImage bufferedImage = new BufferedImage(444, 800, BufferedImage.TYPE_INT_RGB);
        page.print(bufferedImage.getGraphics(), null, 0);
        File file = new File("123.png");


        try {
            ImageIO.write(bufferedImage, "png", file);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
