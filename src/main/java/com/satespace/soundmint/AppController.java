package com.satespace.soundmint;

import com.satespace.soundmint.items.PlaylistBox;
import com.satespace.soundmint.items.PlaylistPane;
import com.satespace.soundmint.items.PlaylistScroller;
import com.satespace.soundmint.items.TrackAudioBar;
import com.satespace.soundmint.items.musicButtons.NextMusicButton;
import com.satespace.soundmint.items.musicButtons.PreviousMusicButton;
import com.satespace.soundmint.items.musicButtons.SwitchStatusMusicButton;
import com.satespace.soundmint.musix.collection.Playlist;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lombok.Getter;

@Getter
public class AppController {
    @FXML protected PlaylistBox topPlayListBlock;
    @FXML protected PlaylistScroller playListScroller;
    @FXML protected PreviousMusicButton previousMusicButton;
    @FXML protected SwitchStatusMusicButton switchStatusMusicButton;
    @FXML protected NextMusicButton nextMusicButton;
    @FXML protected Label trackTitle;
    @FXML protected Label trackArtist;
    @FXML protected TrackAudioBar trackAudioBar;
    @FXML protected StackPane sliderContainer;
    @FXML protected Region fillBar;

    public void initialize() {
        Platform.runLater(() -> {
            this.topPlayListBlock.loadPanes();
            sliderContainer
                    .widthProperty()
                    .addListener((obs, oldVal, newVal) ->
                            trackAudioBar.updateBar(newVal.doubleValue()));
        });
    }

    public PlaylistPane createPlaylistPane(Playlist playlist) {
        PlaylistPane pane = new PlaylistPane(playlist);
        this.topPlayListBlock.getChildren().add(pane);

        return pane;
    }
}