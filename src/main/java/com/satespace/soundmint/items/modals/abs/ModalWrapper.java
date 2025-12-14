package com.satespace.soundmint.items.modals.abs;

import com.satespace.soundmint.App;
import com.satespace.soundmint.controllers.AppController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class ModalWrapper<E extends Modal> extends StackPane {
    public static final int BLUR = 10;
    public static final int BLUR_TIME_MILLIS = 400;

    private final List<Node> bluredNodes = new ArrayList<>();
    private final E modal;
    public ModalWrapper(E modal) {
        this.modal = modal;

        Region background = new Region();
        background.getStyleClass().add("modal-background");
        background.setPrefSize(App.CONTROLLER.getRoot().getWidth(), App.CONTROLLER.getRoot().getHeight());
        background.setOnMouseClicked(e -> this.hide());

        this.getChildren().addAll(background);
        this.setPickOnBounds(false);
    }

    public void show(Double x, Double y) {
        AppController controller = App.CONTROLLER;
        if (!isShowed()) {
            List<ModalWrapper<Modal>> modals = controller.getOpenedModals(Modal.class, null).toList();

            GaussianBlur blur = new GaussianBlur();
            modals.forEach(modal -> {
                if (modal.getEffect() == null || (modal.getEffect() instanceof GaussianBlur b && b.getRadius() < BLUR)) {
                    bluredNodes.add(modal);
                    modal.setEffect(blur);
                }
            });

            BorderPane body = controller.getMainBody();
            Stream.of(body.getTop(), body.getCenter(), body.getBottom())
                    .filter(b -> b != null &&
                            (b.getEffect() == null ||
                            (b.getEffect() instanceof GaussianBlur bb && bb.getRadius() < BLUR)))
                    .forEach(b -> {
                        b.setEffect(blur);
                        bluredNodes.addFirst(b);
                    });

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(blur.radiusProperty(), 0)),
                    new KeyFrame(Duration.millis(BLUR_TIME_MILLIS), new KeyValue(blur.radiusProperty(), BLUR))
            );
            timeline.play();

            controller.getRoot().getChildren().add(this);
            this.getChildren().add(modal);
            this.modal.show(x, y);
        }
    }

    public void hide() {
        this.modal.hide(e -> {
            this.getChildren().remove(modal);
            App.CONTROLLER.getRoot().getChildren().remove(this);

            GaussianBlur blur = new GaussianBlur();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(blur.radiusProperty(), BLUR)),
                    new KeyFrame(Duration.millis(BLUR_TIME_MILLIS), new KeyValue(blur.radiusProperty(), 0))
            );

            Node lastNode = this.bluredNodes.isEmpty() ? null : this.bluredNodes.getLast();
            if (lastNode instanceof ModalWrapper) {
                timeline.setOnFinished(actionEvent -> {
                    if (!(lastNode.getEffect() instanceof GaussianBlur b) || b.getRadius() <= 0) lastNode.setEffect(null);
                });
                lastNode.setEffect(blur);
            }
            else {
                timeline.setOnFinished(actionEvent -> {
                    bluredNodes.forEach(n -> {
                        if (!(n.getEffect() instanceof GaussianBlur b) || b.getRadius() <= 0) n.setEffect(null);
                    });
                });
                this.bluredNodes.forEach(b -> b.setEffect(blur));
            }

            timeline.play();
        });
    }

    public void initLocation(double x, double y) {
        this.modal.initLocation(x, y);
    }

    public boolean isShowed() {
        return this.modal.showed(App.CONTROLLER);
    }
}
