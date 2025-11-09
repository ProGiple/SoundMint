package com.satespace.soundmint;

import com.satespace.soundmint.controllers.AppController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static final String PATH = App.class.getPackageName();
    public static final String TITLE = "SoundMint";
    public static Storage STORAGE;
    public static AppController CONTROLLER;
    public static Stage STAGE;

    @Override
    public void start(Stage stage) throws IOException {
        STAGE = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main/view.fxml"));
        stage.setMaximized(true);
        STORAGE = new Storage();
        Scene scene = new Scene(fxmlLoader.load(), stage.getMaxHeight(), stage.getMaxWidth());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

        CONTROLLER = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exit() {
        Platform.exit();
    }
}