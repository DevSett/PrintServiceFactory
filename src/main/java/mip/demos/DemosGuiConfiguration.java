package mip.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mip.classes.configurations.service.ServiceConfiguration;
import mip.classes.gui.GuiConfiguration;

/**
 * Created by killsett on 16.04.17.
 */
public class DemosGuiConfiguration extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


        ServiceConfiguration serviceConfiguration = new ServiceConfiguration("configurations2");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new GuiConfiguration(serviceConfiguration));
        borderPane.setPrefSize(800,600);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
