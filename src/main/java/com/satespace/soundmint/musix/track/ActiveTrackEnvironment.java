package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.playlist.PlaybackMode;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.util.Utils;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@Getter @Setter
public class ActiveTrackEnvironment {
    private MediaPlayer mediaPlayer;
    private Track activeTrack;
    private final PlaybackMode playbackMode;
    private final Queue<Track> playbackQueue;
    private Playlist activePlaylist;

    public ActiveTrackEnvironment() {
        this.playbackMode = PlaybackMode.SEQUENTIAL;
        this.playbackQueue = new LinkedList<>();
    }

    public void play(Track track, Playlist playlist) {
        this.activeTrack = track;
        this.activePlaylist = playlist;


        if (mediaPlayer != null) {
            if (this.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = new Media(track.getFile().toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);

        App.CONTROLLER.loadLabels();
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!this.isClear()) {
                Duration total = mediaPlayer.getTotalDuration();

                double progress;
                if (total != null && !total.isUnknown() && total.toMillis() > 0) {
                    progress = newTime.toMillis() / total.toMillis();
                    if (progress >= 0.9995) {
                        progress = -1;
                        onTrackEnd();
                    }
                }
                else progress = -1;

                App.CONTROLLER.getCurrentTimeLabel().setText(Utils.formatDuration(newTime));
                App.CONTROLLER.getTrackAudioBar().setProgress(progress, false, mediaPlayer);
            }
        });

        mediaPlayer.play();
    }

    public void resume() {
        if (this.isPaused()) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }


    public void playNext() throws NoSuchElementException {
        Track nextTrack = this.getNext();
        if (nextTrack == null) throw new NoSuchElementException();
        this.play(nextTrack, activePlaylist);
    }

    public void playPrevious() throws NoSuchElementException {
        Track previousTrack = this.getPrevious();
        if (previousTrack == null) throw new NoSuchElementException();
        this.play(previousTrack, activePlaylist);
    }

    public Track getNext() {
        return activePlaylist != null ? activePlaylist.nextTrack(activeTrack, playbackMode) : null;
    }
    public Track getPrevious() {
        return activePlaylist != null ? activePlaylist.previousTrack(activeTrack, playbackMode) : null;
    }


    public void onTrackEnd() {
        mediaPlayer.stop();
        mediaPlayer.dispose();
        App.CONTROLLER.loadLabels();

        try {
            this.playNext();
        } catch (NoSuchElementException e) {
            App.CONTROLLER.getSwitchStatusMusicButton().updateImage(false);
        }
    }


    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public boolean isPaused() {
        return mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED);
    }

    public boolean isClear() {
        return mediaPlayer == null || mediaPlayer.getStatus().equals(MediaPlayer.Status.UNKNOWN) || mediaPlayer.getStatus().equals(MediaPlayer.Status.DISPOSED) || mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED);
    }
}
