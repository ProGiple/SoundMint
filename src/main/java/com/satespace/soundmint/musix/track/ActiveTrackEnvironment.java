package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.musix.collection.Playlist;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@Getter
@Setter
public class ActiveTrackEnvironment {
    private MediaPlayer mediaPlayer;
    private Track activeTrack;
    private Playlist activePlaylist;
    private Track nextTrack;
    private Track previousTrack;

    public void play(Track track, Playlist playlist) {
        this.activeTrack = track;
        this.activePlaylist = playlist;

        int activeTrackIndex = playlist.getTrackList().indexOf(activeTrack);
        if (activeTrackIndex != 0)
            this.previousTrack = playlist.getTrackList().get(activeTrackIndex - 1);
        if (activeTrackIndex != playlist.getTrackList().size() - 1)
            this.nextTrack = playlist.getTrackList().get(activeTrackIndex + 1);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Media media = new Media(track.getFile().toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);

        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void playNext() throws NoSuchElementException {
        if (nextTrack == null)
            throw new NoSuchElementException();
        this.play(nextTrack, activePlaylist);
    }

    public void playPrevious() throws NoSuchElementException {
        if (previousTrack == null)
            throw new NoSuchElementException();
        this.play(previousTrack, activePlaylist);
    }
}
