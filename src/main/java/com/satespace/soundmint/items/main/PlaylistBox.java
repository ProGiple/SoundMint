package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.CollectionPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class PlaylistBox extends HBox {
    public void loadPanes() {
        PlayListCreatePane playListCreatePane = new PlayListCreatePane();
        FavouritesPane favouritesPane = new FavouritesPane();

        this.getChildren().addAll(playListCreatePane, favouritesPane);
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
