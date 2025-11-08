package com.satespace.soundmint.musix.meta;

import lombok.Getter;

@Getter
public abstract class MetaContainer<T extends Meta> {
    private final T metaObject;

    public MetaContainer(T metaObject) {
        this.metaObject = metaObject;
    }
}
