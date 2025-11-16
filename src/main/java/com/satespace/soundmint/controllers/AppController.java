package com.satespace.soundmint.controllers;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.ColoredLabel;
import com.satespace.soundmint.items.abs.HorizontalScroller;
import com.satespace.soundmint.items.main.bit.TrackArtworkImage;
import com.satespace.soundmint.items.modals.abs.Modal;
import com.satespace.soundmint.items.main.*;
import com.satespace.soundmint.items.main.bit.Bit;
import com.satespace.soundmint.items.main.musicButtons.NextMusicButton;
import com.satespace.soundmint.items.main.musicButtons.PreviousMusicButton;
import com.satespace.soundmint.items.main.musicButtons.SwitchStatusMusicButton;
import com.satespace.soundmint.items.modals.abs.ModalWrapper;
import com.satespace.soundmint.items.modals.playlist.PlaylistModal;
import com.satespace.soundmint.musix.meta.TrackMeta;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.util.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
public class AppController extends Controller {
    @FXML protected StackPane root;
    @FXML protected BorderPane mainBody;
    @FXML protected HBox center;
    @FXML protected PlaylistBox topPlayListBlock;
    @FXML protected HorizontalScroller playListScroller;
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
    @FXML protected ColoredLabel selectAudioVolumeLabel;
    @FXML protected ColoredLabel selectAudioTimeLabel;
    @FXML protected StackPane rightButtonsPane;
    @FXML protected RateSpeedButton rateSpeedButton;
    @FXML protected VBox trackBox;
    @FXML protected Bit bit;
    @FXML protected PlaylistModal playlistModal;

    public void initialize() {
        this.loadLabels();
        Platform.runLater(() -> {
            this.topPlayListBlock.loadPanes();
            this.updateTheme();
            this.audioVolumeBar.updateProgress(1.0);

            bit.translateXProperty().bind(trackBox.translateXProperty().subtract(
                    trackBox.widthProperty().get() * 0.6
            ));

            rightButtonsPane.translateXProperty().bind(trackBox.translateXProperty().add(
                    trackBox.widthProperty().get() * 0.7
            ));
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

    @SuppressWarnings("unchecked")
    public <M extends Modal> Stream<ModalWrapper<M>> getOpenedModals(
            Class<M> modalClass,
            Predicate<ModalWrapper<M>> predicate) {
        return this.getRoot().getChildren()
                .stream()
                .filter(n -> n instanceof ModalWrapper<?> wr &&
                        modalClass.isAssignableFrom(wr.getModal().getClass()))
                .map(n -> (ModalWrapper<M>) n)
                .filter(m -> predicate == null || predicate.test(m));
    }
}
