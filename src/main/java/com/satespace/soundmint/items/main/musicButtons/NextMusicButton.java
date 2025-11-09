package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.event.ActionEvent;

public class NextMusicButton extends AbsMusicButton {
    public NextMusicButton() {
        super(SourceImage.NEXT_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (isAllowed()) {
            environment.playNext();
        }
        this.updateState();
        App.CONTROLLER.getSwitchStatusMusicButton().updateImage(!environment.isPlaying());
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
