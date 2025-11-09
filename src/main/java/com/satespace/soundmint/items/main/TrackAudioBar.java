package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.ThemeUpdatable;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;

public class TrackAudioBar extends ProgressBar implements ThemeUpdatable, Clickable<MouseEvent> {
    public TrackAudioBar() {
        this.setProgress(-1);
        this.setOnMouseClicked(this::onClick);
    }

    public void setProgress(double value, boolean updateTrack) {
        MediaPlayer player = App.STORAGE.activeTrackEnvironment().getMediaPlayer();
        if (updateTrack && (player == null || player.getStatus() == MediaPlayer.Status.DISPOSED)) return;

        this.setProgress(value);
        this.theme(App.STORAGE.theme());

        if (updateTrack) {
            if (value >= 0.998) {
                App.STORAGE.activeTrackEnvironment().endHandler();
            }
            else player.seek(player.getTotalDuration().multiply(value));
        }
    }

    @Override
    public void theme(Theme theme) {
        Region region = (Region) this.lookup(".bar");
        if (region != null) {
            region.setStyle("-fx-background-color: #" + theme.getHex() + ";");
        }
    }

    @Override
    public void onClick(MouseEvent event) {
        double progress = event.getX() / this.widthProperty().get();
        this.setProgress(progress, true);
    }
}
