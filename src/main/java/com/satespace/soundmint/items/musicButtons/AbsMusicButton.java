package com.satespace.soundmint.items.musicButtons;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.items.abs.ImagedButton;
import javafx.event.ActionEvent;

public abstract class AbsMusicButton extends ImagedButton {
    public final static int IMAGE_SIZE = 24;

    public AbsMusicButton(SourceImage sourceImage) {
        super(sourceImage, IMAGE_SIZE);
        this.setOnAction(this::onClick);
    }

    protected abstract void onClick(ActionEvent event);
    protected abstract boolean isAllowed();
}
