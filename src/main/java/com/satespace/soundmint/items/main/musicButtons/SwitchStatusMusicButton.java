package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.musix.track.Track;
import com.satespace.soundmint.util.Utils;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SwitchStatusMusicButton extends AbsMusicButton {
    private final ImageView originalPlayedImage;
    private ImageView coloredPlayedImage;
    public SwitchStatusMusicButton() {
        super(SourceImage.TRACK_PAUSED_BUTTON);
        this.updateState();

        this.originalPlayedImage = new ImageView(SourceImage.TRACK_ACTIVE_BUTTON.asImage());
        originalPlayedImage.setFitHeight(IMAGE_SIZE);
        originalPlayedImage.setFitWidth(IMAGE_SIZE);

        this.setOnMouseExited(e -> {
            this.setGraphic(App.STORAGE.activeTrackEnvironment().isPlaying() ?
                    this.originalPlayedImage :
                    this.imageView);
        });
        this.setOnMouseEntered(e -> {
            this.setGraphic(App.STORAGE.activeTrackEnvironment().isPlaying() ?
                    this.coloredPlayedImage :
                    this.recoloredImage);
        });
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (environment.isPlaying()) {
            environment.pause();
        } else if (environment.isClear()) {
            Playlist playlist = App.STORAGE.playlists().getFirst();
            Track track = playlist.getTrackList().getFirst();
            environment.play(track, playlist);
        } else {
            environment.resume();
        }
    }

    @Override
    protected boolean isAllowed() {
        return true;
    }

    public void updateImage(boolean newState) {
        this.setGraphic(newState ? originalPlayedImage : imageView);
    }

    @Override
    public void theme(Theme theme) {
        super.theme(theme);
        coloredPlayedImage = new ImageView(Utils.reColor(this.originalPlayedImage.getImage(), this.color));
        coloredPlayedImage.setFitWidth(IMAGE_SIZE);
        coloredPlayedImage.setFitHeight(IMAGE_SIZE);
    }

    @Override
    public void updateState() {
        super.updateState();

        boolean isPlaying = App.STORAGE.activeTrackEnvironment().isPlaying();
        activedTooltip.setText(isPlaying ? "Поставить на паузу" : "Продолжить воспроизведение");
    }
}
