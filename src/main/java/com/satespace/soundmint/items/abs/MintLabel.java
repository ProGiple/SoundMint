package com.satespace.soundmint.items.abs;

import com.satespace.soundmint.Theme;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Getter;

public abstract class MintLabel extends Label implements ThemeUpdatable, Clickable<MouseEvent> {
    private Paint starterPaint;
    @Getter private Color color;
    @Getter private Tooltip placedTooltip;
    public String strTip;
    public MintLabel() {
        Platform.runLater(() -> {
            if (this.strTip != null) this.setTooltip(strTip);
            this.starterPaint = this.getTextFill();
        });

        this.setOnMouseEntered(e -> {
            this.setTextFill(color);
            this.setUnderline(true);
        });

        this.setOnMouseExited(e -> {
            this.setTextFill(starterPaint);
            this.setUnderline(false);
        });

        this.setOnMouseClicked(this::onClick);
    }

    @Override
    public void theme(Theme theme) {
        this.color = Color.web(theme.getHex());
    }

    public void setTooltip(String value) {
        if (this.placedTooltip != null) {
            Tooltip.uninstall(this, this.placedTooltip);
            if (value == null) return;
        }

        Tooltip t = new Tooltip(value);
        Tooltip.install(this, t);
        this.placedTooltip = t;
    }
}
