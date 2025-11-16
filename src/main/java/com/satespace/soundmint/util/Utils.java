package com.satespace.soundmint.util;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.*;
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

    public Image reColor(Image original, Color targetColor) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();

        WritableImage result = new WritableImage(width, height);
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixel = reader.getColor(x, y);
                double alpha = pixel.getOpacity();
                if (pixel.getRed() > 0.9 && pixel.getGreen() > 0.9 && pixel.getBlue() > 0.9) {
                    Color recolored = new Color(
                            targetColor.getRed(),
                            targetColor.getGreen(),
                            targetColor.getBlue(),
                            alpha
                    );
                    writer.setColor(x, y, recolored);
                } else {
                    writer.setColor(x, y, pixel);
                }
            }
        }

        return result;
    }


    public String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);

        return String.format("%d:%02d", minutes, seconds);
    }

    public void runLater(Runnable runnable, Duration duration) {
        PauseTransition pauseTransition = new PauseTransition(duration);
        pauseTransition.setOnFinished(e -> runnable.run());
        pauseTransition.play();
    }

    public double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }
}
