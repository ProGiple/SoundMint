package com.satespace.soundmint;

import com.satespace.soundmint.musix.collection.Favourites;
import com.satespace.soundmint.musix.collection.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter @Accessors(fluent = true)
public class Storage {
    private final List<Playlist> playlists = new ArrayList<>();
    private final Favourites favourites = new Favourites();

    private final ActiveTrackEnvironment activeTrackEnvironment = new ActiveTrackEnvironment();


    public Storage() {
        playlists.add(new Playlist());
    }
}
