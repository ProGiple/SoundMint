package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.musix.track.Track;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackBox extends HBox {
    public TrackBox(Track track) {
        this.setAlignment(Pos.CENTER_LEFT);

        TrackPane trackPane = new TrackPane(track);
        this.getStyleClass().add("track-playlist-scroller-box-track-box");
        this.getChildren().add(trackPane);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);

        Label trackNameLabel = new TrackLabel(track.getMetaObject().getName());
        Label trackArtistLabel = new TrackLabel(track.getMetaObject().getArtist());
        vBox.getChildren().addAll(trackNameLabel, trackArtistLabel);

        this.getChildren().add(vBox);
    }

    private static class TrackLabel extends Label {
        public TrackLabel(String name) {
            super(name);
            this.getStyleClass().add("track-playlist-scroller-box-track-label");
        }
    }
}
