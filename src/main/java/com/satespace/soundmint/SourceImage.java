package com.satespace.soundmint;

import javafx.scene.image.Image;

import java.io.InputStream;

public enum SourceImage {
    PLAYLIST_DEFAULT_IMAGE,
    NEXT_TRACK_BUTTON,
    PREVIOUS_TRACK_BUTTON,
    TRACK_PLAYED_BUTTON,
    TRACK_PAUSED_BUTTON,
    CREATE_PLAYLIST_BUTTON;

    private final Image image;
    SourceImage() {
        InputStream stream = App.class.getResourceAsStream("images/" + name().toLowerCase() + ".png");
        assert stream != null;
        this.image = new Image(stream);
    }

    public Image asImage() {
        return this.image;
    }
}
