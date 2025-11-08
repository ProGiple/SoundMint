package com.satespace.soundmint;

import javafx.scene.image.Image;

import java.io.InputStream;

public enum SourceImage {
    PLAYLIST_DEFAULT_IMAGE,
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
