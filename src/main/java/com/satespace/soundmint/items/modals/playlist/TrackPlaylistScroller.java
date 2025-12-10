package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.musix.playlist.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TrackPlaylistScroller extends ScrollPane {
    public TrackPlaylistScroller(Playlist playlist) {
        this.getStyleClass().addAll("track-playlist-scroller", "no-border");
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.loadContent(playlist);

        this.setOnScroll(event -> {
            double delta = event.getDeltaY() * 0.003;
            this.setVvalue(Math.max(0, Math.min(1, this.getVvalue() - delta)));
        });
    }

    private void loadContent(Playlist playlist) {
        VBox contents = new VBox();
        contents.setAlignment(Pos.CENTER_LEFT);

        contents.getStyleClass().add("track-playlist-scroller-box");
        playlist.getTrackList().forEach(track -> {
            TrackBox trackBox = new TrackBox(track);
            contents.getChildren().add(trackBox);
        });

        this.setContent(contents);
    }
}
