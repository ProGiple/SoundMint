package com.satespace.soundmint.controllers;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.main.*;
import com.satespace.soundmint.items.main.bit.Bit;
import com.satespace.soundmint.items.main.musicButtons.NextMusicButton;
import com.satespace.soundmint.items.main.musicButtons.PreviousMusicButton;
import com.satespace.soundmint.items.main.musicButtons.SwitchStatusMusicButton;
import com.satespace.soundmint.musix.meta.TrackMeta;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.util.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

@Getter
public class AppController extends Controller {
    @FXML protected PlaylistBox topPlayListBlock;
    @FXML protected PlaylistScroller playListScroller;
    @FXML protected PreviousMusicButton previousMusicButton;
    @FXML protected SwitchStatusMusicButton switchStatusMusicButton;
    @FXML protected NextMusicButton nextMusicButton;
    @FXML protected AudioNameLabel trackTitle;
    @FXML protected Label trackArtist;
    @FXML protected TrackAudioBar trackAudioBar;
    @FXML protected Label currentTimeLabel;
    @FXML protected Label totalTimeLabel;
    @FXML protected AudioVolumeBar audioVolumeBar;
    @FXML protected Label audioVolumeLabel;
    @FXML protected Bit bit;

    public void initialize() {
        this.loadLabels();
        Platform.runLater(() -> {
            this.topPlayListBlock.loadPanes();
            this.updateTheme();
        });
    }

    public PlaylistPane createPlaylistPane(Playlist playlist) {
        PlaylistPane pane = new PlaylistPane(playlist);
        this.topPlayListBlock.getChildren().add(pane);

        return pane;
    }

    public void loadLabels() {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();

        String artist;
        String name;
        String currentTime;
        String totalTime;
        if (environment.getActiveTrack() != null &&
                environment.getMediaPlayer() != null &&
                environment.getMediaPlayer().getStatus() != MediaPlayer.Status.DISPOSED) {
            TrackMeta meta = environment.getActiveTrack().getMetaObject();
            artist = meta.getArtist();
            name = meta.getName();
            currentTime = "0:00";
            totalTime = Utils.formatDuration(Duration.seconds(environment.getActiveTrack().getDuration()));
        }
        else {
            artist = "Неизвестный исполнитель";
            name = "Неизвестное название";
            currentTime = "--:--";
            totalTime = "--:--";
        }

        trackArtist.setText(Utils.cutText(artist, 18));
        trackTitle.setText(Utils.cutText(name, 18));
        trackTitle.setTooltip((String) null);
        currentTimeLabel.setText(currentTime);
        totalTimeLabel.setText(totalTime);
    }
}
