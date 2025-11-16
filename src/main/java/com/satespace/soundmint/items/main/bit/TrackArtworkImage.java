package com.satespace.soundmint.items.main.bit;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.animations.RotateScalingAnimated;
import com.satespace.soundmint.musix.artwork.ArtworkExtractor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class TrackArtworkImage extends Rectangle implements RotateScalingAnimated {
    public static int SIZE = 125;
    public static int ARC = 75;

    public TrackArtworkImage() {
        super(SIZE, SIZE);
        this.setStrokeWidth(2);

        this.setArcHeight(ARC);
        this.setArcWidth(ARC);

        this.updateImage();

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));

        this.setEffect(shadow);
        this.setOnMouseEntered(e -> this.playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER, this));
        this.setOnMouseExited(e -> this.playAnimation(0, 1.0, this));
    }

    public void updateImage() {
        com.satespace.soundmint.musix.track.Track track = App.STORAGE.activeTrackEnvironment().getActiveTrack();
        File file = track == null ? null : track.getFile();

        Image image = ArtworkExtractor.extractArtwork(file);
        this.setFill(new ImagePattern(image));
    }

    public void updateStroke(Color color) {
        this.setStroke(color);
    }
}
