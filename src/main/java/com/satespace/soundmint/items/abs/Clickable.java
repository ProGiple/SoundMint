package com.satespace.soundmint.items.abs;

import javafx.event.Event;

public interface Clickable<E extends Event> {
    void onClick(E event);
}
