package com.satespace.soundmint;

import com.satespace.soundmint.musix.Playlist;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter @Accessors(fluent = true)
public class Storage {
    private final List<Playlist> playlists = new ArrayList<>();
    public Storage() {

    }
}
