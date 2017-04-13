package mip.classes.configurations.service;

import mip.classes.configurations.Configuration;
import mip.classes.configurations.Sticker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by killsett on 13.04.17.
 */
public class ServiceConfiguration {
    private File fileToConfigurations;
    private List<Configuration> configurations;
    private ConfigurationSelectionModel selectionModel;


    public ServiceConfiguration(String pathToConfigurations) {
        this.fileToConfigurations = new File(pathToConfigurations);
        parse();
    }

    public File getFileToConfigurations() {
        return fileToConfigurations;
    }

    public void setFileToConfigurations(File fileToConfigurations) {
        this.fileToConfigurations = fileToConfigurations;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public void parse() {
        File[] files = fileToConfigurations.listFiles();
        configurations = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            if (
                    file.isFile()
                            &&
                            "json".equals(
                                    file.getName().substring(
                                            file.getName().lastIndexOf(".") + 1,
                                            file.getName().length()
                                    )
                            )
                    ) {
                configurations.add(new Configuration(file.getPath()));
            }
        });
        selectionModel = new ConfigurationSelectionModel(configurations);

    }

    public ConfigurationSelectionModel getSelectionModel() {
        return selectionModel;
    }


    public static void main(String[] args) {
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration("configurations");
        serviceConfiguration.getConfigurations().forEach(configuration -> {
            System.out.println(configuration.getStickers().size());
            for (Sticker sticker : configuration.getStickers()) {
                System.out.println(sticker.getOrientation());
            }
        });
    }
}
