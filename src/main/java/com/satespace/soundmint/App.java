package com.satespace.soundmint;

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


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
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