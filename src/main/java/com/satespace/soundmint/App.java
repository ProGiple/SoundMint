package com.satespace.soundmint;

import com.satespace.soundmint.controllers.AppController;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {
    public static final String PATH = App.class.getPackageName();
    public static final String TITLE = "SoundMint";
    public static Storage STORAGE;
    public static AppController CONTROLLER;
    public static Stage STAGE;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main/view.fxml"));
        stage.setMaximized(true);

        STORAGE = new Storage();

        Scene scene = new Scene(fxmlLoader.load(), stage.getMaxHeight() / 1.5, stage.getMaxWidth() / 1.25);
        this.setKeyListener(scene);
        Parent rootNode = scene.getRoot();
        rootNode.setFocusTraversable(true);
        Platform.runLater(rootNode::requestFocus);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

        STAGE = stage;
        CONTROLLER = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exit() {
        Platform.exit();
    }

    private void setKeyListener(Scene scene) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            MediaPlayer player = environment.getMediaPlayer();
            switch (keyEvent.getCode()) {
                case RIGHT -> player.seek(player.getCurrentTime().add(Duration.seconds(5)));
                case LEFT -> player.seek(player.getCurrentTime().subtract(Duration.seconds(5)));
                case UP -> player.setVolume(player.getVolume() + 0.1);
                case DOWN -> player.setVolume(player.getVolume() - 0.1);
            }
        });
    }
}