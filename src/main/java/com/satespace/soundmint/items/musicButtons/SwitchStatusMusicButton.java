package com.satespace.soundmint.items.musicButtons;

import com.satespace.soundmint.SourceImage;
import javafx.event.ActionEvent;

public class SwitchStatusMusicButton extends AbsMusicButton {
    private boolean isPlayed = false;
    public SwitchStatusMusicButton() {
        super(SourceImage.TRACK_PAUSED_BUTTON);
    }

    @Override
    protected void onClick(ActionEvent event) {
        this.isPlayed = !this.isPlayed;

        SourceImage image = this.isPlayed ? SourceImage.TRACK_PLAYED_BUTTON : SourceImage.TRACK_PAUSED_BUTTON;
        this.replaceImage(image, IMAGE_SIZE);
    }

    @Override
    protected boolean isAllowed() {
        return false;
    }
}
