package com.satespace.soundmint.musix.collection;

import com.satespace.soundmint.musix.meta.MetaContainer;
import com.satespace.soundmint.musix.track.Track;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist extends MetaContainer<PlaylistMeta> {
    private final List<Track> trackList;

    public Playlist() {
        super(new PlaylistMeta());
        this.trackList = new ArrayList<>();

    }

    public void addTrack(Track track) {
        this.trackList.add(track);
    }

    public void removeTrack(Track track) {
        this.trackList.remove(track);
    }
}
