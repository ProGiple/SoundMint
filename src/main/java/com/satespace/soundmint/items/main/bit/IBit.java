package com.satespace.soundmint.items.main.bit;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public interface IBit {
    double getTranslateX();
    double getTranslateY();
    double getMultiplier();
    void scaled(double value);

    default RotateTransition registerRotate(Node node) {
        RotateTransition rot = new RotateTransition(Duration.seconds(3), node);
        rot.setByAngle(360);
        rot.setCycleCount(Animation.INDEFINITE);
        rot.setInterpolator(Interpolator.LINEAR);
        return rot;
    }

    default Timeline registerDrift(Node node) {
        Timeline driftTimeline = new Timeline();
        driftTimeline.setCycleCount(Animation.INDEFINITE);
        driftTimeline.setAutoReverse(true);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        double timeMultiplier = random.nextDouble(0.5, 1.75);
        driftTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), getTranslateX()),
                        new KeyValue(node.translateYProperty(), getTranslateY())
                ),
                new KeyFrame(Duration.seconds(4 * timeMultiplier),
                        new KeyValue(node.translateXProperty(), getTranslateX() + random.nextInt(-40, 40)),
                        new KeyValue(node.translateYProperty(), getTranslateY() + random.nextInt(-35, 30))
                ),
                new KeyFrame(Duration.seconds(8 * timeMultiplier),
                        new KeyValue(node.translateXProperty(), getTranslateX() - random.nextInt(-40, 40)),
                        new KeyValue(node.translateYProperty(), getTranslateY() - random.nextInt(-35, 30))
                ),
                new KeyFrame(Duration.seconds(12 * timeMultiplier),
                        new KeyValue(node.translateXProperty(), getTranslateX())
                )
        );

        Collections.shuffle(driftTimeline.getKeyFrames());
        return driftTimeline;
    }
}
