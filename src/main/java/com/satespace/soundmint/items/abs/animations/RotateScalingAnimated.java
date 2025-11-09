package com.satespace.soundmint.items.abs.animations;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public interface RotateScalingAnimated {
    int ROTATE_ANGLE = 5;
    double SIZE_MULTIPLIER = 0.9;
    default void playAnimation(int rotateAngle, double sizeMultiplier, Node node) {
        RotateTransition rt = new RotateTransition(Duration.millis(250), node);
        ScaleTransition st = new ScaleTransition(Duration.millis(150), node);

        rt.setToAngle(rotateAngle);
        st.setToX(sizeMultiplier);
        st.setToY(sizeMultiplier);

        rt.play();
        st.play();
    }
}
