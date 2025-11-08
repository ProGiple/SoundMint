package com.satespace.soundmint.musix.track;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveTrackEnvironment {
    private MediaPlayer mediaPlayer;
    private Track activeTrack;

    public void play(Track track) {
        this.activeTrack = track;
        mediaPlayer.stop();
        mediaPlayer.dispose();
        Media media = new Media(track.getFile().toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }
}
