package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.ImagedButton;
import com.satespace.soundmint.items.abs.ThemeUpdatable;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Setter;

@Setter
public abstract class AbsMusicButton extends ImagedButton implements Clickable<ActionEvent>, ThemeUpdatable {
    public static final int IMAGE_SIZE = 16;
    public static final double IN_DISABLED_OPACITY = 0.3;

    protected Color color;
    protected ImageView recoloredImage;
    protected Tooltip activedTooltip = new Tooltip();
    public AbsMusicButton(SourceImage sourceImage) {
        super(sourceImage, IMAGE_SIZE);
        Tooltip.install(this, activedTooltip);

        this.setOnAction(this::onClick);
        this.setOnMouseExited(e -> {
            this.setGraphic(imageView);
        });
        this.setOnMouseEntered(e -> {
            this.setGraphic(recoloredImage);
        });
    }

    protected abstract boolean isAllowed();

    public void updateState() {
        double state = isAllowed() ? 1.0 : IN_DISABLED_OPACITY;
        if (state == this.getOpacity()) return;

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(230), this);
        fadeTransition.setToValue(state);
        fadeTransition.play();
    }

    @Override
    public void theme(Theme theme) {
        this.color = Color.web(theme.getHex());
        this.recoloredImage = new ImageView(Utils.reColor(this.imageView.getImage(), this.color));
        recoloredImage.setFitWidth(IMAGE_SIZE);
        recoloredImage.setFitHeight(IMAGE_SIZE);
    }
}
