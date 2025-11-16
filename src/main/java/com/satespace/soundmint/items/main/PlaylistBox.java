package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.CollectionPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlaylistBox extends HBox {
    public void loadPanes() {
        PlayListCreatePane playListCreatePane = new PlayListCreatePane();
        FavouritesPane favouritesPane = new FavouritesPane();

        VBox vBox = new VBox(playListCreatePane, new OpenSettingsPane());
        vBox.setSpacing(10);

        this.getChildren().addAll(vBox, favouritesPane);
        for (int i = 0; i < App.STORAGE.playlists().size(); i++) {
            App.CONTROLLER.createPlaylistPane(App.STORAGE.playlists().get(i));
        }
    }

    public List<CollectionPane> getPanes() {
        return this.getChildren()
                .stream()
                .filter(c -> c instanceof CollectionPane)
                .map(c -> (CollectionPane) c)
                .toList();
    }
}