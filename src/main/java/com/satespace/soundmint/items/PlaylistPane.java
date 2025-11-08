package com.satespace.soundmint.items;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.Playlist;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlaylistPane extends Pane {
    public static final int ROTATE_ANGLE = 10;
    public static final double SIZE_MULTIPLIER = 0.8;

    private final Playlist playlist;
    public PlaylistPane(Playlist playlist) {
        this.playlist = playlist;
        this.getStyleClass().addAll("playlist-pane", "playlist-base-pane");

        this.setOnMouseClicked(e -> App.exit());
        this.setOnMouseEntered(e -> playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER));
        this.setOnMouseExited(e -> playAnimation(0, 1.0));
    }

    private void playAnimation(int rotateAngle, double sizeMultiplier) {
        RotateTransition rt = new RotateTransition(Duration.millis(250), this);
        ScaleTransition st = new ScaleTransition(Duration.millis(150), this);

        rt.setToAngle(rotateAngle);
        st.setToX(sizeMultiplier);
        st.setToY(sizeMultiplier);

        rt.play();
        st.play();
    }
}
