package com.satespace.soundmint.items.main.bit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BitSquare extends Rectangle {

    public BitSquare(int width,
                     int height) {
        this.setWidth(100);
        this.setHeight(100);
        this.setFill(Color.BLUE);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }
}
