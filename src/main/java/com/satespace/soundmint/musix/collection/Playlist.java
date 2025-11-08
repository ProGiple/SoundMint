package com.satespace.soundmint.musix.collection;

import com.satespace.soundmint.musix.track.Track;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist {
    private final List<Track> trackList;
    private Image image;

    public Playlist() {
        this.trackList = new ArrayList<>();
    }

    public void addTrack(Track track) {
        this.trackList.add(track);
    }

    public void removeTrack(Track track) {
        this.trackList.remove(track);
    }
}
