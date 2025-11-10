package com.satespace.soundmint.items.main.bit;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@Getter
public class BitCircle extends Circle implements IBit {
    private final double multiplier;

    @Builder
    public BitCircle(int radius,
                     double multiplier,
                     int blur,
                     Function<ObjectProperty<Color>, Timeline> timelineFunction,
                     int translateX,
                     int translateY,
                     boolean hasFill) {
        super(radius);
        this.multiplier = multiplier;

        this.setStrokeWidth(6);
        this.setEffect(new GaussianBlur(blur));

        ObjectProperty<Color> dynamicColor = new SimpleObjectProperty<>(Color.RED);
        this.strokeProperty().bind(dynamicColor);
        if (hasFill) this.fillProperty().bind(dynamicColor);
        else this.setFill(null);

        Timeline timeline = timelineFunction.apply(dynamicColor);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);

        RotateTransition rotateTransition = this.registerRotate(this);
        rotateTransition.play();

        Timeline driftTimeline = this.registerDrift(this);
        driftTimeline.play();
    }

    @Override
    public void scaled(double value) {
        this.setScaleX(value * multiplier);
        this.setScaleY(value * multiplier);
    }
}
