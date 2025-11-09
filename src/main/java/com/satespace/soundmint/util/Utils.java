package com.satespace.soundmint.util;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
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

    public static Image reColor(Image inputImage, Color oldColor, Color newColor) {
        int W = (int) inputImage.getWidth();
        int H = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();
        int ob=(int) oldColor.getBlue()*255;
        int or=(int) oldColor.getRed()*255;
        int og=(int) oldColor.getGreen()*255;
        int nb=(int) newColor.getBlue()*255;
        int nr=(int) newColor.getRed()*255;
        int ng=(int) newColor.getGreen()*255;
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int argb = reader.getArgb(x, y);
                int a = (argb >> 24) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >>  8) & 0xFF;
                int b =  argb        & 0xFF;
                if (g==og && r==or && b==ob) {
                    r=nr;
                    g=ng;
                    b=nb;
                }

                argb = (a << 24) | (r << 16) | (g << 8) | b;
                writer.setArgb(x, y, argb);
            }
        }
        return outputImage;
    }

    public String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%d:%02d", minutes, seconds);
    }
}
