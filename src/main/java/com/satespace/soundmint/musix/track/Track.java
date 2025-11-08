package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.musix.TrackMeta;
import com.satespace.soundmint.musix.artwork.ArtworkExtractor;
import com.satespace.soundmint.musix.collection.Playlist;
import com.satespace.soundmint.musix.meta.MetaContainer;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;

@Getter
public class Track extends MetaContainer<TrackMeta> {
    private final Playlist playlist;
    private final Track next;
    private final Track previous;
    private final File file;
    private final Image artwork;
    private final int duration;

    public Track(Playlist playlist, TrackMeta trackMeta, File file, int duration) {
        super(trackMeta);
        this.playlist = playlist;
        this.file = file;
        this.duration = duration;
        this.artwork = ArtworkExtractor.extractArtwork(file);

        int indexSelf = playlist.getTrackList().indexOf(this);
        this.next = playlist.getTrackList().get(indexSelf + 1);
        this.previous = playlist.getTrackList().get(indexSelf - 1);
    }
}
