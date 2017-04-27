package mip.classes.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import mip.classes.configurations.Configuration;
import mip.classes.configurations.Converter;
import mip.classes.configurations.Row;
import mip.classes.configurations.Sticker;
import mip.classes.configurations.service.ServiceConfiguration;
import mip.classes.helpers.TextFieldFormatter;

/**
 * Created by killsett on 16.04.17.
 */
public class GuiConfiguration extends BorderPane {
    public final static String CONNECTORS = "Connectors";
    public final static String ROWS = "Rows";
    public final static String STICKERS = "Stickers";
//    public final static String CONVERTERS = "Converters";

    private ServiceConfiguration serviceConfiguration;

    private ComboBox<String> comboBoxSelectTypeConfiguration;
    private ComboBox<String> comboBoxNamesSelectedConfiguration;

    public GuiConfiguration(ServiceConfiguration serviceConfiguration) {
        super();
        this.serviceConfiguration = serviceConfiguration;
        processing();
        init();
    }

    private void processing() {
        HBox hBoxUp = new HBox(10);
        comboBoxSelectTypeConfiguration = new ComboBox<>();
        comboBoxNamesSelectedConfiguration = new ComboBox<>();

        HBox.setHgrow(comboBoxSelectTypeConfiguration, Priority.SOMETIMES);
        HBox.setHgrow(comboBoxNamesSelectedConfiguration, Priority.SOMETIMES);
        comboBoxNamesSelectedConfiguration.setMaxWidth(Double.MAX_VALUE);
        comboBoxSelectTypeConfiguration.setMaxWidth(Double.MAX_VALUE);

        hBoxUp.setPadding(new Insets(5, 5, 5, 5));

        hBoxUp.getChildren().addAll(comboBoxSelectTypeConfiguration, comboBoxNamesSelectedConfiguration);
        setTop(hBoxUp);

        HBox hBoxDown = new HBox(10);
    }

    public void init() {
        comboBoxSelectTypeConfiguration.getItems().clear();
        comboBoxSelectTypeConfiguration.getItems().addAll(
                GuiConfiguration.CONNECTORS,
                GuiConfiguration.ROWS,
                GuiConfiguration.STICKERS
//                GuiConfiguration.CONVERTERS
        );
        comboBoxSelectTypeConfiguration.
                getSelectionModel().
                selectedItemProperty().
                addListener(((observable, oldValue, newValue) -> showConfigurationsName(newValue)));

        comboBoxNamesSelectedConfiguration.getSelectionModel().
                selectedItemProperty().
                addListener(((observable, oldValue, newValue) -> showConfigurationInfo(newValue)));


    }

    private void showConfigurationsName(String value) {

        comboBoxNamesSelectedConfiguration.getItems().clear();

        switch (value) {
            case GuiConfiguration.CONNECTORS:
                for (Configuration configuration : serviceConfiguration.getConfigurations()) {
                    String pathConnectors = configuration.getPath();
                    comboBoxNamesSelectedConfiguration.getItems().addAll(getName(pathConnectors));
                }
                break;
            case GuiConfiguration.ROWS:
                for (Configuration configuration : serviceConfiguration.getConfigurations()) {
                    String pathRow = configuration.getRow().getPath();
                    comboBoxNamesSelectedConfiguration.getItems().addAll(getName(pathRow));
                }
                break;
            case GuiConfiguration.STICKERS:
                for (Configuration configuration : serviceConfiguration.getConfigurations()) {
                    for (Sticker sticker : configuration.getStickers()) {
                        String name = sticker.getName();
                        boolean check = true;
                        for (String nameBox : comboBoxNamesSelectedConfiguration.getItems()) {
                            if (nameBox.equals(name)) check = false;
                        }
                        if (check) comboBoxNamesSelectedConfiguration.getItems().add(name);
                    }
                }
                break;
            /*case GuiConfiguration.CONVERTERS: {
                for (Configuration configuration : serviceConfiguration.getConfigurations()) {
                    for (Converter converter : configuration.getConverters()) {
                        String pathConverter = converter.getName();
                        boolean check = true;
                        for (String nameBox : comboBoxNamesSelectedConfiguration.getItems()) {
                            if (nameBox.equals(getName(pathConverter))) check = false;
                        }
                        if (check) comboBoxNamesSelectedConfiguration.getItems().add(getName(pathConverter));
                    }
                }
                break;
            }*/
            default:
                return;
        }
    }

    private void showConfigurationInfo(String value) {
        //gui и загрузка данных
        switch (comboBoxSelectTypeConfiguration.getSelectionModel().getSelectedItem()) {
            case GuiConfiguration.CONNECTORS:
                initConnectors(value);
                break;
            case GuiConfiguration.STICKERS:
                initStickers(value);
                break;
            case GuiConfiguration.ROWS:
                initRows(value);
                break;
        }
    }

    private void initRows(String value) {
        clearWindow();

        GridPane gridPane = new GridPane();
        setCenter(gridPane);
        gridPane.setHgap(300);
        gridPane.setVgap(5);
        Label labelCountStickers = new Label("Кол-во стикеров");
        Label labelTabSize = new Label("Отступ между стикерами в мм");
        Label labelStartStickerSize = new Label("Отступ от первой наклейки в мм");

        TextField fieldCountStickers = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldCountStickers.setEditable(false);

        TextField fieldTabSize = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldTabSize.setEditable(false);

        TextField fieldStartStickersSize = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldStartStickersSize.setEditable(false);

        fieldStartStickersSize.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        gridPane.add(labelCountStickers, 0, 0);
        gridPane.add(labelTabSize, 0, 1);
        gridPane.add(labelStartStickerSize, 0, 2);
        gridPane.add(fieldCountStickers, 1, 0);
        gridPane.add(fieldTabSize, 1, 1);
        gridPane.add(fieldStartStickersSize, 1, 2);
        for (ColumnConstraints columnConstraints : gridPane.getColumnConstraints()) {
            columnConstraints.setHgrow(Priority.SOMETIMES);
        }
        for (RowConstraints rowConstraints : gridPane.getRowConstraints()) {
            rowConstraints.setVgrow(Priority.SOMETIMES);
        }
        //загрузка данных

        Row row = null;
        for (Configuration configuration : serviceConfiguration.getConfigurations()) {
            if (getName(configuration.getRow().getPath()).equals(value)) {
                row = configuration.getRow();
            }
        }
        if (row == null) return;

        fieldCountStickers.setText(String.valueOf(row.getCountSticker()));
        fieldStartStickersSize.setText(String.valueOf(Math.round(row.getStartStickerSizePx() / 2.8d)));
        fieldTabSize.setText(String.valueOf(Math.round(row.getTabSizePx() / 2.8)));

        //tt только чтение

    }

    private void initStickers(String value) {
        clearWindow();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(300);
        gridPane.setVgap(5);
        setCenter(gridPane);

        Label labelWidth = new Label("Ширина");
        Label labelHeight = new Label("Высота");
        TextField fieldWidth = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldWidth.setEditable(false);
        TextField fieldHeight = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldHeight.setEditable(false);

        Label labelPaddingUp = new Label("Отступ сверху:");
        Label labelPaddingLeft = new Label("Отступ слева:");
        TextField fieldPaddingUp = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldPaddingUp.setEditable(false);
        TextField fieldPaddingLeft = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldPaddingLeft.setEditable(false);

        Label labelOrientation = new Label("Ориентация");
        ComboBox<String> box = new ComboBox();
        box.getItems().addAll("Горизантально", "Вертикально");

        Label labelFontSize = new Label("Размер шрифта");
        Label labelFontName = new Label("Наименование шрифта");

        TextField fieldFontSize = new TextFieldFormatter("00", TextFieldFormatter.INT_MATCHES);
        fieldFontSize.setEditable(false);
        TextField fieldFontName = new TextField("");
        fieldFontName.setEditable(false);

        gridPane.add(labelWidth, 0, 0);
        gridPane.add(fieldWidth, 1, 0);
        gridPane.add(labelHeight, 0, 1);
        gridPane.add(fieldHeight, 1, 1);
        gridPane.add(labelPaddingUp, 0, 2);
        gridPane.add(fieldPaddingUp, 1, 2);
        gridPane.add(labelPaddingLeft, 0, 3);
        gridPane.add(fieldPaddingLeft, 1, 3);
        gridPane.add(labelOrientation, 0, 4);
        gridPane.add(box, 1, 4);
        gridPane.add(labelFontSize, 0, 5);
        gridPane.add(fieldFontSize, 1, 5);
        gridPane.add(labelFontName, 0, 6);
        gridPane.add(fieldFontName, 1, 6);

        //загрузка данных

        Sticker sticker = null;
        for (Configuration configuration : serviceConfiguration.getConfigurations()) {
            for (Sticker stickerT : configuration.getStickers()) {
                if (getName(stickerT.getName()).equals(value)) {
                    sticker = stickerT;
                }
            }
        }
        if (sticker == null) return;

        Sticker.Size size = sticker.getSize();
        fieldWidth.setText(String.valueOf(Math.round(size.getWidth() / 2.8d)));
        fieldHeight.setText(String.valueOf(Math.round(size.getHeight() / 2.8d)));

        Sticker.Padding padding = sticker.getPadding();
        fieldPaddingUp.setText(String.valueOf(Math.round(padding.getUp() / 2.8d)));
        fieldPaddingLeft.setText(String.valueOf(Math.round(padding.getLeft() / 2.8d)));

        Sticker.FontCustom fontCustom = sticker.getFontCustom();
        fieldFontName.setText(fontCustom.getFont());
        fieldFontSize.setText(String.valueOf(fontCustom.getFontSize()));

        if ("landscape".equals(sticker.getOrientation())) box.getSelectionModel().select(1);
        else box.getSelectionModel().select(0);

        //tt только чтение
    }


    private void initConnectors(String value) {
        clearWindow();
        BorderPane borderPane = new BorderPane();
        setCenter(borderPane);

        VBox vBox = new VBox(10);
        borderPane.setLeft(vBox);

        Button buttonStickers = new Button("Stickers");
        buttonStickers.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(buttonStickers, Priority.SOMETIMES);
        vBox.getChildren().addAll(buttonStickers);

        Button buttonConverters = new Button("Converters");
        buttonConverters.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(buttonConverters, Priority.SOMETIMES);
        vBox.getChildren().add(buttonConverters);

        Button buttonRow = new Button("Row");
        buttonRow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(buttonRow, Priority.SOMETIMES);
        vBox.getChildren().add(buttonRow);


        buttonStickers.setOnAction(event -> initStickersFromConnectors(value, borderPane));

        buttonConverters.setOnAction(event -> initConvertersFromConnectors(value, borderPane));

        buttonRow.setOnAction(event -> initRowFromConnectors(value, borderPane));
    }

    private void clearWindow() {
        setCenter(null);
        setLeft(null);
        setRight(null);
    }

    private void initRowFromConnectors(String value, BorderPane borderPane) {
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(5, 5, 5, 5));

        TextField textField = new TextField();
        textField.setEditable(false);

        hBox.getChildren().addAll(textField);

        borderPane.setCenter(hBox);

        //установки данных
        for (Configuration configuration : serviceConfiguration.getConfigurations()) {
            if (getName(configuration.getPath()).equals(value))
                textField.setText(getName(configuration.getRow().getPath()));
        }
        //tt только просмотр
    }

    private void initConvertersFromConnectors(String value, BorderPane borderPane) {
        ListView<String> listView = new ListView<>();
        listView.setPadding(new Insets(5, 5, 5, 5));
        borderPane.setCenter(listView);

        //установка данных
        for (Configuration configuration : serviceConfiguration.getConfigurations()) {
            if (getName(configuration.getPath()).equals(value))
                for (Converter converter : configuration.getConverters()) {
                    listView.getItems().add(getName(converter.getName()));
                }
        }
        //tt только просмотр
    }

    private void initStickersFromConnectors(String value, BorderPane borderPane) {
        ListView<String> listView = new ListView<>();
        borderPane.setCenter(listView);
        listView.setPadding(new Insets(5, 5, 5, 5));


        //установка данных
        for (Configuration configuration : serviceConfiguration.getConfigurations()) {
            if (getName(configuration.getPath()).equals(value))
                for (Sticker sticker : configuration.getStickers()) {
                    listView.getItems().add(getName(sticker.getName()));
                }
        }
        //tt только просмотр
    }

    private String getName(String pathConnectors) {

        if (pathConnectors.indexOf('/') != -1)
            return pathConnectors.substring(pathConnectors.lastIndexOf('/') + 1, pathConnectors.length());
        if (pathConnectors.indexOf('\\') != -1)
            return pathConnectors.substring(pathConnectors.lastIndexOf('\\') + 1, pathConnectors.length());
        return pathConnectors;
    }


}
