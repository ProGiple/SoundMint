package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.SourceImage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import lombok.Getter;

@Getter
public class ImagedButton extends Button {
    protected ImageView imageView;
    public ImagedButton(SourceImage sourceImage, int size) {
        this.replaceImage(sourceImage, size);
        this.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
    }

    protected ImageView replaceImage(SourceImage sourceImage, int size) {
        ImageView imageView = new ImageView(sourceImage.asImage());
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        this.setGraphic(imageView);
        this.imageView = imageView;
        return imageView;
    }
}
