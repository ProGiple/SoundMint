package com.satespace.soundmint.musix.playlist;

import com.satespace.soundmint.musix.meta.MetaContainer;
import com.satespace.soundmint.musix.meta.PlaylistMeta;
import com.satespace.soundmint.musix.track.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist extends MetaContainer<PlaylistMeta> {
    private final List<Track> trackList;
    @Setter
    private PlaybackMode playbackMode = PlaybackMode.SEQUENTIAL;

    public Playlist(PlaylistMeta meta, PlaybackMode playbackMode) {
        super(meta);
        this.trackList = new ArrayList<>();
        this.playbackMode = playbackMode;
    }

    public Playlist() {
        super(new PlaylistMeta());
        this.trackList = new ArrayList<>();
    }

    public Track nextTrack(Track track) {
        int currentIndex = trackList.indexOf(track);

        if (currentIndex != trackList.size() - 1) {
            return trackList.get(currentIndex + 1);
        } else return null;

    }

    public Track previousTrack(Track track) {
        int currentIndex = trackList.indexOf(track);

        if (currentIndex != 0) {
            return trackList.get(currentIndex - 1);
        } else return null;

    }

    public void addTrack(Track track) {
        this.trackList.add(track);
    }

    public void removeTrack(Track track) {
        this.trackList.remove(track);
    }
}
