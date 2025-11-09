package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.Theme;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public abstract class ClickableBar extends ProgressBar implements Clickable<MouseEvent>, ThemeUpdatable {
    public ClickableBar() {
        this.setProgress(-1);
        this.setOnMouseClicked(this::onClick);
    }

    @Override
    public void theme(Theme theme) {
        Region region = (Region) this.lookup(".bar");
        if (region != null) {
            region.setStyle("-fx-background-color: #" + theme.getHex() + ";");
        }
    }
}
