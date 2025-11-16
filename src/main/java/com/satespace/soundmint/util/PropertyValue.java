package com.satespace.soundmint.util;

import javafx.beans.value.ObservableValueBase;
import lombok.Getter;

@Getter
public class PropertyValue<E> extends ObservableValueBase<E> {
    private E value;
    public PropertyValue(E value) {
        this.value = value;
    }

    public PropertyValue() {
    }

    public void setValue(E value) {
        if (this.value == value) return;
        this.value = value;
        fireValueChangedEvent();
    }
}
