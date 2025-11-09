package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.util.Utils;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public abstract class CollectionPane extends Pane implements Clickable<MouseEvent> {
    public static final int IMAGE_FIT_HEIGHT = 40;
    public static final double IMAGE_OPACITY = 0.2;
    public static final int ROTATE_ANGLE = 5;
    public static final double SIZE_MULTIPLIER = 0.9;

    public CollectionPane(Image image) {
        this.getStyleClass().add("collection-base-pane");

        StackPane stackPane = Utils.setNodeImage(this, image, IMAGE_OPACITY, IMAGE_FIT_HEIGHT);
        this.getChildren().add(stackPane);

        this.setOnMouseEntered(e -> playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER));
        this.setOnMouseExited(e -> playAnimation(0, 1.0));
        this.setOnMouseClicked(this::onClick);
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
