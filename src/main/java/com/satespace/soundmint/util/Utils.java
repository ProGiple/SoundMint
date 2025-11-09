package com.satespace.soundmint.util;

import com.satespace.soundmint.Theme;
import javafx.geometry.Pos;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public StackPane setNodeImage(Region region,
                                  Image image,
                                  double imageOpacity,
                                  int imageFitHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setOpacity(imageOpacity);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(imageFitHeight);

        StackPane stackPane = new StackPane(imageView);
        stackPane.prefHeightProperty().bind(region.heightProperty());
        stackPane.prefWidthProperty().bind(region.widthProperty());
        stackPane.setAlignment(Pos.CENTER);
        return stackPane;
    }

    public String cutText(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    public StackPane hoverRecolor(ImageView imageView, Color color) {
        Rectangle overlay = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight(), color);
        overlay.setBlendMode(BlendMode.MULTIPLY);
        overlay.setOpacity(0);

        StackPane stackPane = new StackPane(imageView, overlay);

        stackPane.setOnMouseEntered(e -> overlay.setOpacity(1));
        stackPane.setOnMouseExited(e -> overlay.setOpacity(0));
        return stackPane;
    }

    public String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%d:%02d", minutes, seconds);
    }
}
