package com.satespace.soundmint.musix;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist extends ArrayList<Music> {
    private Image image;
    public Playlist() {

    }
}
