package com.satespace.soundmint.musix;

import com.satespace.soundmint.musix.artwork.ArtworkExtractor;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;

@Getter
public class Track {
    private final File file;
    private final TrackMeta info;
    private final Image artwork;
    private final int duration;

    public Track(File file, TrackMeta info, int duration) {
        this.info = info;
        this.file = file;
        this.artwork = ArtworkExtractor.extractArtwork(file);
        this.duration = duration;
    }
}
