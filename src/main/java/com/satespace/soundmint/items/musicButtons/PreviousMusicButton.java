package com.satespace.soundmint.items.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import javafx.event.ActionEvent;

public class PreviousMusicButton extends AbsMusicButton {
    public PreviousMusicButton() {
        super(SourceImage.PREVIOUS_TRACK_BUTTON);
    }

    @Override
    protected void onClick(ActionEvent event) {
        App.STORAGE.activeTrackEnvironment().playPrevious();
    }

    @Override
    protected boolean isAllowed() {
        return false;
    }
}
