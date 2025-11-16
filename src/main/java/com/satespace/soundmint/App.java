package com.satespace.soundmint;

import com.satespace.soundmint.controllers.AppController;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
        STAGE = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main/view.fxml"));
        stage.setMaximized(true);

        STORAGE = new Storage();

        Scene scene = new Scene(fxmlLoader.load(), stage.getMaxHeight() / 1.5, stage.getMaxWidth() / 1.25);
        CONTROLLER = fxmlLoader.getController();

        this.setKeyListener(scene);
        Parent rootNode = scene.getRoot();
        rootNode.setFocusTraversable(true);
        Platform.runLater(rootNode::requestFocus);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exit() {
        Platform.exit();
    }

    private void setKeyListener(Scene scene) {
        ActiveTrackEnvironment env = App.STORAGE.activeTrackEnvironment();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            MediaPlayer player = env.getMediaPlayer();
            switch (keyEvent.getCode()) {
                case RIGHT -> {
                    double ms = Math.min(5000,
                            player.getTotalDuration().toMillis() -
                            player.getCurrentTime().toMillis() - 10);
                    player.seek(player.getCurrentTime().add(Duration.millis(ms)));
                }
                case LEFT -> player.seek(player.getCurrentTime().subtract(Duration.seconds(5)));
                case UP -> env.getVolume().setValue(Math.min(player.getVolume() + 0.05, 1.0));
                case DOWN -> env.getVolume().setValue(Math.max(player.getVolume() - 0.05, 0));
            }
        });
    }
}