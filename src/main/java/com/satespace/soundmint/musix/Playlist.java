package com.satespace.soundmint.musix;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class Playlist extends LinkedList<Track> {
    @Setter
    private String name;
    private Image image;

}
