package com.satespace.soundmint.controllers;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.collection.Playlist;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class PlaylistController extends Controller {
    @Override
    public void initialize() {

    }

    @SneakyThrows
    public static PlaylistController open(Playlist playlist) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("playlist/view.fxml"));

        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(App.STAGE);

        Scene scene = new Scene(fxmlLoader.load(), modal.getMaxHeight(), modal.getMaxWidth());
        modal.setScene(scene);
        modal.setTitle(App.TITLE + "- Плейлист " + playlist.getMetaObject().getName());
        modal.showAndWait();

        return fxmlLoader.getController();
    }
}
