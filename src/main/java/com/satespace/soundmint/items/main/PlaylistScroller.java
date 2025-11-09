package com.satespace.soundmint.items.main;

import javafx.scene.control.ScrollPane;

public class PlaylistScroller extends ScrollPane {
    public PlaylistScroller() {
        this.setOnScroll(event -> {
            double delta = event.getDeltaY() * 0.003;
            this.setHvalue(Math.max(0, Math.min(1, this.getHvalue() - delta)));
        });
    }
}
