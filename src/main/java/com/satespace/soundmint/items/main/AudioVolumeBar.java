package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.ClickableBar;
import javafx.scene.input.MouseEvent;

public class AudioVolumeBar extends ClickableBar {
    @Override
    public void onClick(MouseEvent event) {
        double progress = event.getX() / this.widthProperty().get();
        this.setProgress(progress);
        this.theme(App.STORAGE.theme());
        App.CONTROLLER.getAudioVolumeLabel().setText(String.format("%.0f", progress * 100) + "%");
    }
}
