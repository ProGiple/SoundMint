package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.ClickableBar;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioVolumeBar extends ClickableBar {
    public static final int FADE_MILLIS = 100;

    public AudioVolumeBar() {
        super();

        this.setOnMouseMoved(e -> {
            double progress = e.getX() / this.widthProperty().get();

            Label label = App.CONTROLLER.getSelectAudioVolumeLabel();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_MILLIS), label);
            if (label.getOpacity() <= 0) {
                fadeTransition.setToValue(0.75);
                fadeTransition.play();
            }
            label.setText(String.format("%.0f", progress * 100) + "%");
        });

        this.setOnMouseExited(e -> {
            Label label = App.CONTROLLER.getSelectAudioVolumeLabel();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_MILLIS), label);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });
    }

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
