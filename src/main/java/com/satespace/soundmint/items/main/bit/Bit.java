package com.satespace.soundmint.items.main.bit;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Bit extends StackPane {
    public Bit() {
        Circle innerCircle = new Circle(50, Color.TRANSPARENT);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(2);

        Circle back = new Circle(150, Color.TRANSPARENT);
        back.setStroke(Color.TRANSPARENT);
        back.setOpacity(0);

        this.getChildren().addAll(innerCircle, back);
        this.setAlignment(Pos.CENTER);
    }

    public void initialize(boolean hided) {
        this.getChildren().removeIf(c -> c instanceof IBit);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 12; i++) {
            Color color1 = Color.rgb(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255));
            Color color2 = Color.rgb(
                    random.nextInt(255),
                    random.nextInt(255),
                    random.nextInt(255));
            BitCircle circle = BitCircle.builder()
                    .radius(random.nextInt(25, 100))
                    .multiplier(random.nextDouble(0.4, 0.9))
                    .blur(random.nextInt(50, 75))
                    .timelineFunction(p -> new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(p, color1)),
                            new KeyFrame(Duration.seconds(random.nextDouble(1.7, 4)),
                                    new KeyValue(p, color2))
                    ))
                    .translateX(random.nextInt(-75, 75))
                    .translateY(random.nextInt(-80, 80))
                    .build();

            if (hided) circle.setOpacity(0);
            this.getChildren().add(circle);
        }
    }

    public void setScale(double value) {
        this.getChildren()
                .stream()
                .filter(c -> c instanceof IBit)
                .forEach(c -> ((IBit) c).scaled(value));
    }

    public void hide(boolean moment) {
        if (moment) this.getChildren()
                .stream()
                .filter(c -> c instanceof IBit)
                .forEach(c -> c.setOpacity(0));
        else this.fadeAnimation(0);
    }

    public void show() {
        this.fadeAnimation(1);
    }

    private void fadeAnimation(double value) {
        for (int i = 0; i < this.getChildren().size(); i++) {
            Node node = this.getChildren().get(i);
            if (node instanceof IBit) {
                PauseTransition pauseTransition = new PauseTransition(Duration.millis(75 * i));
                pauseTransition.setOnFinished(e -> {
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), node);
                    fadeTransition.setToValue(value);
                    fadeTransition.setCycleCount(1);
                    fadeTransition.setAutoReverse(false);
                    fadeTransition.play();
                });
                pauseTransition.play();
            }
        }
    }
}
