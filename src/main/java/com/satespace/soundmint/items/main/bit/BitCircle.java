package com.satespace.soundmint.items.main.bit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
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
                     int translateY) {
        super(radius);
        this.multiplier = multiplier;

        this.setFill(null);
        this.setStrokeWidth(6);
        this.setEffect(new GaussianBlur(blur));

        ObjectProperty<Color> dynamicColor = new SimpleObjectProperty<>(Color.RED);
        this.strokeProperty().bind(dynamicColor);

        Timeline timeline = timelineFunction.apply(dynamicColor);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
    }

    @Override
    public void scaled(double value) {
        this.setScaleX(value * multiplier);
        this.setScaleY(value * multiplier);
    }
}
