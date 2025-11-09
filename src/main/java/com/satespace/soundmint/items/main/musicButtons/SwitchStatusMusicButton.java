package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.collection.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.musix.track.Track;
import javafx.event.ActionEvent;

public class SwitchStatusMusicButton extends AbsMusicButton {
    public SwitchStatusMusicButton() {
        super(SourceImage.TRACK_PAUSED_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (environment.isNowPlayed()) {
            environment.pause();
        }
        else if (!environment.play()) {
            Playlist playlist = App.STORAGE.playlists().getFirst();
            Track track = playlist.getTrackList().getFirst();
            environment.play(track, playlist);
        }

        this.updateImage(environment.isNowPlayed());
    }

    @Override
    protected boolean isAllowed() {
        return true;
    }

    public void updateImage(boolean isNowPlayed) {
        this.replaceImage(isNowPlayed ?
                SourceImage.TRACK_PLAYED_BUTTON :
                SourceImage.TRACK_PAUSED_BUTTON, IMAGE_SIZE);
    }
}
