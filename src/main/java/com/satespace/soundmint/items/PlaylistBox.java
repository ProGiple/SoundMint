package com.satespace.soundmint.items;

import com.satespace.soundmint.App;
import javafx.scene.layout.HBox;

import java.util.List;

public class PlaylistBox extends HBox {
    public void loadPanes() {
        PlayListCreatePane playListCreatePane = new PlayListCreatePane();
        this.getChildren().add(playListCreatePane);
        for (int i = 0; i < App.STORAGE.playlists().size(); i++) {
            App.CONTROLLER.createPlaylistPane(App.STORAGE.playlists().get(i));
        }
    }

    public List<PlaylistPane> getPanes() {
        return this.getChildren()
                .stream()
                .filter(c -> c instanceof PlaylistPane)
                .map(c -> (PlaylistPane) c)
                .toList();
    }
}
