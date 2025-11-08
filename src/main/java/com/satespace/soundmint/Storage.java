package com.satespace.soundmint;

import com.satespace.soundmint.musix.TrackLoader;
import com.satespace.soundmint.musix.collection.Favourites;
import com.satespace.soundmint.musix.collection.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.musix.track.Track;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Accessors(fluent = true)
public class Storage {
    private final List<Playlist> playlists = new ArrayList<>();
    private final Favourites favourites = new Favourites();

    private final ActiveTrackEnvironment activeTrackEnvironment = new ActiveTrackEnvironment();

    private final List<Track> loadedTracks = new ArrayList<>();

    public Storage() {
        playlists.add(new Playlist());
        String userHome = System.getProperty("user.home");
        File musicDir = new File(userHome, "Music");
        for (File file: Objects.requireNonNull(musicDir.listFiles())) {
            loadedTracks.add(TrackLoader.loadTrack(file));
        }
    }

}
