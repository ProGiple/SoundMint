package com.satespace.soundmint;

import lombok.experimental.UtilityClass;

import java.util.prefs.Preferences;

@UtilityClass
public class AppState {
    private final Preferences prefs = Preferences.userNodeForPackage(Storage.class);

    private final String LAST_TRACK = "last_track";
    private final String LAST_PLAYLIST = "last_playlist";
}
