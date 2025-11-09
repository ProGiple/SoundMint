package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.TrackMeta;
import com.satespace.soundmint.musix.collection.Playlist;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@Getter @Setter
public class ActiveTrackEnvironment {
    private MediaPlayer mediaPlayer;
    private Track activeTrack;
    private Playlist activePlaylist;
    private Track nextTrack;
    private Track previousTrack;
    private boolean nowPlayed = false;
    public void play(Track track, Playlist playlist) {
        this.activeTrack = track;
        this.activePlaylist = playlist;

        int activeTrackIndex = playlist.getTrackList().indexOf(activeTrack);
        if (activeTrackIndex != 0) {
            this.previousTrack = playlist.getTrackList().get(activeTrackIndex - 1);
        }

        if (activeTrackIndex != playlist.getTrackList().size() - 1) {
            this.nextTrack = playlist.getTrackList().get(activeTrackIndex + 1);
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = new Media(track.getFile().toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);

        App.CONTROLLER.loadLabels();
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (mediaPlayer.getStatus() != MediaPlayer.Status.DISPOSED) {
                Duration total = mediaPlayer.getTotalDuration();

                double progress;
                if (total != null && !total.isUnknown() && total.toMillis() > 0) {
                    progress = newTime.toMillis() / total.toMillis();
                    if (progress >= 0.9995) {
                        progress = -1;
                        endHandler();
                    }
                }
                else progress = -1;
                App.CONTROLLER.getTrackAudioBar().setProgress(progress, false);
            }
        });

        mediaPlayer.play();
        nowPlayed = true;
    }

    public boolean play() {
        if (mediaPlayer == null) return false;

        mediaPlayer.play();
        if (this.isNowPlayed()) {

        }

        nowPlayed = true;
        return true;
    }

    public void pause() {
        mediaPlayer.pause();
        nowPlayed = false;
    }

    public void playNext() throws NoSuchElementException {
        if (nextTrack == null) throw new NoSuchElementException();
        this.play(nextTrack, activePlaylist);
    }

    public void playPrevious() throws NoSuchElementException {
        if (previousTrack == null) throw new NoSuchElementException();
        this.play(previousTrack, activePlaylist);
    }

    public void endHandler() {
        nowPlayed = false;
        App.CONTROLLER.getSwitchStatusMusicButton().updateImage(false);
        mediaPlayer.stop();
        mediaPlayer.dispose();
        App.CONTROLLER.loadLabels();
    }
}
