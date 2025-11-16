package com.satespace.soundmint.items.main;

import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.ThemeUpdatable;
import com.satespace.soundmint.util.PropertyValue;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Getter;

public class RateSpeedButton extends Button implements ThemeUpdatable, Clickable<MouseEvent> {
    private Color color;
    @Getter private final PropertyValue<Double> rate = new PropertyValue<>();
    public RateSpeedButton() {
        super();

        this.rate.setValue(1.0);
        this.setOnMouseClicked(this::onClick);

        this.setOnScroll(event -> {
            double value = event.getDeltaY() < 0 ? -0.05 : 0.05;
            this.rate.setValue(Math.max(0.25, Math.min(3.0, this.rate.getValue() + value)));
            this.updateState();
        });

        Tooltip tooltip = new Tooltip("Нажимайте кнопками мыши или скролльте колёсиком для изменения скорости");
        Tooltip.install(this, tooltip);

        this.prefWidthProperty().bind(Bindings.createDoubleBinding(
                this::getHeight,
                this.heightProperty()
        ));
        this.updateState();
    }

    private void updateState() {
        this.setText(String.format("x%.2f", rate.getValue()));
    }

    @Override
    public void theme(Theme theme) {
        this.color = Color.web(theme.getHex());
    }

    @Override
    public void onClick(MouseEvent event) {
        double value;

        MouseButton button = event.getButton();
        if (button == MouseButton.PRIMARY) {
            value = 0.25;
        }
        else if (button == MouseButton.MIDDLE) {
            this.rate.setValue(1.0);
            this.updateState();
            return;
        }
        else if (button == MouseButton.SECONDARY) {
            value = -0.25;
        }
        else return;

        this.rate.setValue(Math.max(0.25, Math.min(9.5, this.rate.getValue() + value)));
        this.updateState();
    }
}
