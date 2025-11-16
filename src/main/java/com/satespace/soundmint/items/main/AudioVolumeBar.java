package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.ClickableBar;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.util.PropertyValue;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

@Getter
public class AudioVolumeBar extends ClickableBar {
    public static final int FADE_MILLIS = 100;

    public AudioVolumeBar() {
        super();
        this.setOnMouseDragged(e -> {
            if (App.STORAGE.activeTrackEnvironment().isClear()) {
                return;
            }

            double progress = e.getX() / this.widthProperty().get();
            if (progress < 0 || progress > 1) {
                return;
            }

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
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (environment.isClear()) {
            return;
        }

        double progress = event.getX() / this.widthProperty().get();
        environment.getVolume().setValue(progress);
        this.theme(App.STORAGE.theme());
    }

    public void updateProgress(double d) {
        this.setProgress(d);
        App.CONTROLLER.getAudioVolumeLabel().setText(String.format("%.0f", d * 100) + "%");
    }
}
