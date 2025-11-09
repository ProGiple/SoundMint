package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import javafx.event.ActionEvent;

public class NextMusicButton extends AbsMusicButton {
    public NextMusicButton() {
        super(SourceImage.NEXT_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        if (isAllowed()) {
            App.STORAGE.activeTrackEnvironment().playNext();
        }
        this.updateState();
    }

    @Override
    protected boolean isAllowed() {
        return App.STORAGE.activeTrackEnvironment().getNext() != null;
    }

    @Override
    public void updateState() {
        super.updateState();
        this.replaceImage(SourceImage.NEXT_TRACK_BUTTON, IMAGE_SIZE);
    }
}
