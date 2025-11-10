package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.playlist.Playlist;
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
        if (environment.isPlaying()) {
            environment.pause();
        } else if (environment.isClear()) {
            Playlist playlist = App.STORAGE.playlists().getFirst();
            Track track = playlist.getTrackList().getFirst();
            environment.play(track, playlist, true);
        } else {
            environment.resume();
        }

        App.CONTROLLER.getNextMusicButton().updateState();
        App.CONTROLLER.getPreviousMusicButton().updateState();
        this.updateImage(!environment.isPlaying());
    }

    @Override
    protected boolean isAllowed() {
        return true;
    }

    public void updateImage(boolean newState) {
        this.replaceImage(newState ?
                SourceImage.TRACK_ACTIVE_BUTTON :
                SourceImage.TRACK_PAUSED_BUTTON, IMAGE_SIZE);
    }
}
