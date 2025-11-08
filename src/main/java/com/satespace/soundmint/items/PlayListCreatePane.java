package com.satespace.soundmint.items;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.Playlist;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayListCreatePane extends Pane {
    public PlayListCreatePane() {
        this.getStyleClass().addAll("playlist-base-pane", "create-playlist-pane");

        HBox.setMargin(this, new Insets(0, 20, 0, 10));
        this.setOnMouseClicked(e -> {
            Playlist playlist = new Playlist();
            App.STORAGE.playlists().add(playlist);

            PlaylistPane pane = App.CONTROLLER.createPlaylistPane(playlist);
            pane.setScaleX(0);
            pane.setScaleY(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(225), pane);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }
}
