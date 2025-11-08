package com.satespace.soundmint.items.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.collection.Playlist;
import com.satespace.soundmint.musix.track.Track;
import javafx.event.ActionEvent;

public class SwitchStatusMusicButton extends AbsMusicButton {
    private boolean isPlayed = false;
    public SwitchStatusMusicButton() {
        super(SourceImage.TRACK_PAUSED_BUTTON);
    }

    @Override
    protected void onClick(ActionEvent event) {
        this.isPlayed = !isPlayed;

        SourceImage image = isPlayed ? SourceImage.TRACK_PLAYED_BUTTON : SourceImage.TRACK_PAUSED_BUTTON;
        this.replaceImage(image, IMAGE_SIZE);
        if (!isPlayed) {
            Playlist playlist = App.STORAGE.playlists().getFirst();
            Track track = playlist.getTrackList().getFirst();
            App.STORAGE.activeTrackEnvironment().play(track, playlist);
        }
    }

    @Override
    protected boolean isAllowed() {
        return false;
    }
}
