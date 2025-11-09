package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import javafx.event.ActionEvent;

public class PreviousMusicButton extends AbsMusicButton {
    public PreviousMusicButton() {
        super(SourceImage.PREVIOUS_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        if (isAllowed()) {
            App.STORAGE.activeTrackEnvironment().playPrevious();
        }
        this.updateState();
    }

    @Override
    protected boolean isAllowed() {
        return App.STORAGE.activeTrackEnvironment().getPreviousTrack() != null;
    }
}
