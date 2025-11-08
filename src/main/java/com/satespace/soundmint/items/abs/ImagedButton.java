package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.SourceImage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ImagedButton extends Button {
    public ImagedButton(SourceImage sourceImage, int size) {
        this.replaceImage(sourceImage, size);
        this.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
    }

    protected void replaceImage(SourceImage sourceImage, int size) {
        ImageView imageView = new ImageView(sourceImage.asImage());
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        this.setGraphic(imageView);
    }
}
