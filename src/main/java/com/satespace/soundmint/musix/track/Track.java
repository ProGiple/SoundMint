package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.musix.meta.TrackMeta;
import com.satespace.soundmint.musix.artwork.ArtworkExtractor;
import com.satespace.soundmint.musix.meta.MetaContainer;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;

@Getter
public class Track extends MetaContainer<TrackMeta> {
    private final File file;
    private final Image artwork;
    private final double duration;

    public Track(TrackMeta trackMeta, File file, double duration) {
        super(trackMeta);
        this.file = file;
        this.duration = duration;
        this.artwork = ArtworkExtractor.extractArtwork(file);
    }
}
