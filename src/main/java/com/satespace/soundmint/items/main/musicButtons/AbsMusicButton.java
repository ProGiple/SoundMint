package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.ImagedButton;
import com.satespace.soundmint.items.abs.ThemeUpdatable;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class AbsMusicButton extends ImagedButton implements Clickable<ActionEvent> {
    public final static int IMAGE_SIZE = 16;
    public static final double IN_DISABLED_OPACITY = 0.3;
    public AbsMusicButton(SourceImage sourceImage) {
        super(sourceImage, IMAGE_SIZE);
        this.setOnAction(this::onClick);
    }

    protected abstract boolean isAllowed();

    public void updateState() {
        double state = isAllowed() ? 1.0 : IN_DISABLED_OPACITY;
        if (state == this.getOpacity()) return;

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(230), this);
        fadeTransition.setToValue(state);
        fadeTransition.play();
    }

    public ImageView replaceImage(SourceImage sourceImage, int size) {
        ImageView imageView = super.replaceImage(sourceImage, size);
        if (!this.isAllowed()) return imageView;

        Color color = Color.web(App.STORAGE.theme().getHex());
        this.setGraphic(Utils.hoverRecolor(this.imageView, color));
        return imageView;
    }
}
