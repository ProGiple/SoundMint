package com.satespace.soundmint.items.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import javafx.event.ActionEvent;

public class NextMusicButton extends AbsMusicButton {
    public NextMusicButton() {
        super(SourceImage.NEXT_TRACK_BUTTON);
    }

    @Override
    protected void onClick(ActionEvent event) {
        App.STORAGE.activeTrackEnvironment().playNext();
    }

    @Override
    protected boolean isAllowed() {
        return false;
    }
}
