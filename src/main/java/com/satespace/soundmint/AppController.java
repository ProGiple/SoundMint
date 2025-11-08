package com.satespace.soundmint;

import com.satespace.soundmint.items.PlayListCreatePane;
import com.satespace.soundmint.items.PlaylistPane;
import com.satespace.soundmint.musix.Playlist;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AppController {
    @FXML protected HBox topPlayListBlock;
    @FXML protected ScrollPane playListScroller;

    public void initialize() {
        PlayListCreatePane playListCreatePane = new PlayListCreatePane();
        this.topPlayListBlock.getChildren().add(playListCreatePane);
        for (int i = 0; i < App.STORAGE.playlists().size(); i++) {
            this.createPlaylistPane(App.STORAGE.playlists().get(i));
        }

        this.playListScroller.setOnScroll(event -> {
            double delta = event.getDeltaY() * 0.003;
            this.playListScroller.setHvalue(Math.max(0, Math.min(1, this.playListScroller.getHvalue() - delta)));
        });
    }

    public PlaylistPane createPlaylistPane(Playlist playlist) {
        PlaylistPane pane = new PlaylistPane(playlist);
        this.topPlayListBlock.getChildren().add(pane);

        return pane;
    }
}