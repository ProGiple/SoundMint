package com.satespace.soundmint.items.main;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.util.Utils;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class OpenSettingsPane extends Pane implements Clickable<MouseEvent> {
    public static final double IMAGE_OPACITY = 0.2;
    public static final int IMAGE_FIT_HEIGHT = 25;

    public OpenSettingsPane() {
        this.getStyleClass().addAll("mini-collection-base-pane", "settings-button");

        Image image = SourceImage.SETTINGS_BUTTON.asImage();
        StackPane stackPane = Utils.setNodeImage(this, image, IMAGE_OPACITY, IMAGE_FIT_HEIGHT);
        this.getChildren().add(stackPane);

        this.setOnMouseClicked(this::onClick);
    }

    @Override
    public void onClick(MouseEvent event) {

    }
}
