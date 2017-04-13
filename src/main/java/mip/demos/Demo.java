package mip.demos;

import mip.classes.configurations.Configuration;
import mip.classes.configurations.converter.GenerateStickerConverter;
import mip.classes.page.FabricPage;
import mip.classes.page.Page;
import mip.classes.printservice.PrintService;
import mip.classes.stickers.StickerSpace;
import mip.classes.stickers.StickerString;
import mip.demos.data.Person;
import mip.interfaces.StickerObject;
import mip.interfaces.ToStickerConverter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Пример
 */
public class Demo {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ToStickerConverter<Person> converter = person -> {
            List<StickerObject> stickerObjects = new ArrayList<>();
            stickerObjects.add(new StickerString("Имя: " + person.getName()));
            stickerObjects.add(new StickerString("Муж: " + person.getSex()));
            stickerObjects.add(new StickerSpace(11));
            stickerObjects.add(new StickerString("Возраст: " + person.getAge()));
            stickerObjects.add(new StickerString("Почта: " + person.getEmailAddress()));
            return stickerObjects;
        };

        List<Person> people = Person.createRoster();


        List<StickerObject>[] lists = new List[3];
        lists[0] = converter.convert(people.get(0));
        lists[1] = converter.convert(people.get(1));
        lists[2] = converter.convert(people.get(2));


        Configuration configuration = new Configuration("Connector1.json");


        ToStickerConverter<Person> converter1 = new GenerateStickerConverter().create(configuration.getConverters().get(0));



        lists[0] = converter1.convert(people.get(0));

        for (StickerObject stickerObject : lists[0]) {
            if (stickerObject.getClass().getSimpleName().equals("StickerString")) {
                System.out.println(((StickerString)stickerObject).getLine());
            }
        }

        configuration.getConverters().get(0).getObjectFields().forEach(stickerObjectConverter -> System.out.println(stickerObjectConverter.getClass().getSimpleName()));
        Page[] pages = new FabricPage(configuration).getPages(lists);

        PrintService printService = new PrintService();
        printService.print(pages);

    }
}
