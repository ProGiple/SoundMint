package com.satespace.soundmint.items.modals.abs;

import com.satespace.soundmint.App;
import com.satespace.soundmint.controllers.AppController;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.function.Consumer;

public abstract class Modal extends GridPane {
    public static final double OPEN_MILLIS = 200;

    public Modal(double sizeMap) {
        this.getStyleClass().add("modal");
        this.setVisible(false);
        this.setOpacity(0);

        this.maxWidthProperty().bind(App.STAGE.widthProperty().multiply(sizeMap));
        this.maxHeightProperty().bind(App.STAGE.heightProperty().multiply(sizeMap));
    }

    public abstract boolean showed(AppController controller);

    public void initLocation(Double x, Double y) {
        this.setTranslateX(x != null ? x : 0);
        this.setTranslateY(y != null ? y : 0);
    }

    public void show(Double x, Double y) {
        this.setVisible(true);
        if (x != null || y != null) {
            this.initLocation(x, y);
            TranslateTransition transition = new TranslateTransition(Duration.millis(OPEN_MILLIS), this);

            transition.setToX(0);
            transition.setToY(0);

            transition.play();
        }

        ScaleTransition scale = new ScaleTransition(Duration.millis(OPEN_MILLIS), this);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();

        FadeTransition fade = new FadeTransition(Duration.millis(OPEN_MILLIS / 2), this);
        fade.setToValue(1.0);
        fade.play();
    }

    public void hide(Consumer<ActionEvent> consumer) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(OPEN_MILLIS), this);
        scale.setToX(0);
        scale.setToY(0);
        scale.play();

        FadeTransition fade = new FadeTransition(Duration.millis(OPEN_MILLIS * 0.9), this);
        fade.setFromValue(1.0);
        if (consumer != null) fade.setOnFinished(consumer::accept);
        fade.play();
    }

    public static <M extends Modal> ModalWrapper<M> open(M modal, Node node) {
        ModalWrapper<M> modalWrapper = new ModalWrapper<>(modal);

        Bounds bounds = modalWrapper.localToScene(node.getBoundsInLocal());
        Point2D local = modalWrapper.sceneToLocal(
                bounds.getMinX() + bounds.getWidth() / 2,
                bounds.getMinY() + bounds.getHeight() / 2
        );

        modalWrapper.show(local.getX(), local.getY());
        return modalWrapper;
    }
}
