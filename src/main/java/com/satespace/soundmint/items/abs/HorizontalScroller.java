package com.satespace.soundmint.items.abs;

import javafx.scene.control.ScrollPane;

public class HorizontalScroller extends ScrollPane {
    public HorizontalScroller() {
        this.setOnScroll(event -> {
            double delta = event.getDeltaY() * 0.003;
            this.setHvalue(Math.max(0, Math.min(1, this.getHvalue() - delta)));
        });
    }
}
