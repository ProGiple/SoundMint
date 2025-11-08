package com.satespace.soundmint.items;

import com.satespace.soundmint.App;
import javafx.application.Platform;
import javafx.scene.control.Slider;

public class TrackAudioBar extends Slider {
    public TrackAudioBar() {
        Platform.runLater(() -> this.updateBar(this.getValue()));
        this.valueProperty().addListener(
                (obs, oldVal, newVal) ->
                        this.updateBar(newVal.doubleValue()));
    }

    public void updateBar(double value) {
        double totalWidth = App.CONTROLLER.getSliderContainer().getWidth();
        double fillWidth = Math.max(0, Math.min(value * totalWidth, totalWidth));
        App.CONTROLLER.getFillBar().setPrefWidth(fillWidth);
    }
}
