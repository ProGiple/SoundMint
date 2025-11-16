package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.items.abs.animations.RotateScalingAnimated;
import com.satespace.soundmint.util.Utils;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class CollectionPane extends Pane implements Clickable<MouseEvent>, RotateScalingAnimated {
    public static final int IMAGE_FIT_HEIGHT = 40;
    public static final double IMAGE_OPACITY = 0.2;

    public CollectionPane(Image image) {
        this.getStyleClass().add("collection-base-pane");

        StackPane stackPane = Utils.setNodeImage(this, image, IMAGE_OPACITY, IMAGE_FIT_HEIGHT);
        this.getChildren().add(stackPane);

        this.setOnMouseEntered(e -> playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER, this));
        this.setOnMouseExited(e -> playAnimation(0, 1.0, this));
        this.setOnMouseClicked(this::onClick);
    }
}
