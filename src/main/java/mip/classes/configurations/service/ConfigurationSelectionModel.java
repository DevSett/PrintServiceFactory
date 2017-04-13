package mip.classes.configurations.service;

import javafx.scene.control.SelectionModel;
import mip.classes.configurations.Configuration;

import java.util.List;

/**
 * Created by killsett on 13.04.17.
 */
public class ConfigurationSelectionModel extends SelectionModel<Configuration> {

    private List<Configuration> configurationList;

    public ConfigurationSelectionModel(List<Configuration> configurationList) {
        this.configurationList = configurationList;
    }

    public Configuration select(String nameConfiguration) {
        for (int index = 0; index < configurationList.size(); index++) {
            Configuration configuration = configurationList.get(index);
            String path = configuration.getPath();
            if (path.indexOf(nameConfiguration) != -1) {
                select(index);
                return configuration;
            }
        }
        clearSelection();
        return null;
    }

    @Override
    public void clearAndSelect(int index) {
        clearSelection(index);
        select(index);
    }

    @Override
    public void select(int index) {
        if (index < configurationList.size()) {
            setSelectedItem(configurationList.get(index));
            setSelectedIndex(index);
        }

    }

    @Override
    public void select(Configuration obj) {
        for (int index = 0; index < configurationList.size(); index++) {
            Configuration configuration = configurationList.get(index);
            if (configuration.equals(obj)) {
                setSelectedItem(getSelectedItem());
                setSelectedIndex(getSelectedIndex());
            }
        }
    }

    @Override
    public void clearSelection(int index) {
        setSelectedIndex(index);
    }

    @Override
    public void clearSelection() {
        setSelectedIndex(-1);
        setSelectedItem(null);
    }

    @Override
    public boolean isSelected(int index) {
        return getSelectedIndex() == index ? true : false;
    }

    @Override
    public boolean isEmpty() {
        return configurationList.isEmpty() ? true : false;
    }

    @Override
    public void selectPrevious() {
        if (getSelectedIndex() > 0)
            select(getSelectedIndex() - 1);
    }

    @Override
    public void selectNext() {
        if (getSelectedIndex() + 1 != configurationList.size())
            select(getSelectedIndex() + 1);
    }

    @Override
    public void selectFirst() {
        if (!isEmpty())
            select(0);
    }

    @Override
    public void selectLast() {
        if (!isEmpty()) select(configurationList.size() - 1);
    }
}
