package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.ClickableBar;
import com.satespace.soundmint.items.abs.ThemeUpdatable;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class TrackAudioBar extends ClickableBar {
    public static int FADE_MILLIS = 100;

    public TrackAudioBar() {
        super();

        this.setOnMouseMoved(e -> {
            if (App.STORAGE.activeTrackEnvironment().isClear()) {
                return;
            }

            double progress = e.getX() / this.widthProperty().get();

            Label label = App.CONTROLLER.getSelectAudioTimeLabel();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_MILLIS), label);
            if (label.getOpacity() <= 0) {
                fadeTransition.setToValue(0.75);
                fadeTransition.play();
            }

            Duration total = App.STORAGE.activeTrackEnvironment().getMediaPlayer().getTotalDuration();
            label.setText(Utils.formatDuration(total.multiply(progress)));
        });

        this.setOnMouseExited(e -> {
            Label label = App.CONTROLLER.getSelectAudioTimeLabel();
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(FADE_MILLIS), label);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });
    }

    public void setProgress(double value, boolean updateTrack, MediaPlayer player) {
        if (updateTrack && (player == null || player.getStatus() == MediaPlayer.Status.DISPOSED)) return;

        this.setProgress(value);
        this.theme(App.STORAGE.theme());

        if (updateTrack) {
            if (value >= 0.998) {
                App.STORAGE.activeTrackEnvironment().onTrackEnd();
            }
            else player.seek(player.getTotalDuration().multiply(value));
        }
    }

    @Override
    public void onClick(MouseEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (!environment.isClear()) {
            double progress = event.getX() / this.widthProperty().get();
            this.setProgress(progress, true, environment.getMediaPlayer());
        }
    }
}
