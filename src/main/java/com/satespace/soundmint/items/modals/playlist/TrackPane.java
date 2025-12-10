package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.musix.track.Track;
import com.satespace.soundmint.util.Utils;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;

@Getter
public class TrackPane extends Pane implements Clickable<MouseEvent> {
    public static final int IMAGE_FIT_HEIGHT = 55;
    public static final double IMAGE_OPACITY = 0.8;

    private Track track;
    public TrackPane(Track track) {
        this.getStyleClass().add("playlist-track-pane");

        Image image = track.getArtwork();
        StackPane stackPane = Utils.setNodeImage(this, image, IMAGE_OPACITY, IMAGE_FIT_HEIGHT);
        this.getChildren().add(stackPane);

        this.setOnMouseClicked(this::onClick);
    }

    @Override
    public void onClick(MouseEvent event) {

    }
}
