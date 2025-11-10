package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.Theme;
import javafx.scene.control.Label;

public class ColoredLabel extends Label implements ThemeUpdatable {
    public ColoredLabel() {
    }

    @Override
    public void theme(Theme theme) {
        this.setStyle("-fx-text-fill: #" + theme.getHex() + ";");
    }
}
