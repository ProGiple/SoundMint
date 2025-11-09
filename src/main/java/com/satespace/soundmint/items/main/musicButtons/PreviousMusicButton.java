package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.event.ActionEvent;

public class PreviousMusicButton extends AbsMusicButton {
    public PreviousMusicButton() {
        super(SourceImage.PREVIOUS_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (isAllowed()) {
            environment.playPrevious();
        }
    }

    @Override
    protected boolean isAllowed() {
        System.out.println(App.STORAGE.activeTrackEnvironment().getPrevious());
        return App.STORAGE.activeTrackEnvironment().getPrevious() != null;
    }
}
