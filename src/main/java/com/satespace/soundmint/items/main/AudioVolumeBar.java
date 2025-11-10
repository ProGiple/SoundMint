package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.ClickableBar;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.scene.input.MouseEvent;

public class AudioVolumeBar extends ClickableBar {
    @Override
    public void onClick(MouseEvent event) {
        double progress = event.getX() / this.widthProperty().get();
        this.setProgress(progress);
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        environment.getMediaPlayer().setVolume(progress);
        this.theme(App.STORAGE.theme());
        App.CONTROLLER.getAudioVolumeLabel().setText(String.format("%.0f", progress * 100) + "%");
    }
}
