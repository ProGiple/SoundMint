package com.satespace.soundmint.controllers;

import com.satespace.soundmint.App;
import com.satespace.soundmint.Theme;
import com.satespace.soundmint.items.abs.ThemeUpdatable;

import java.lang.reflect.Field;

public abstract class Controller {
    public abstract void initialize();
    public void updateTheme() {
        try {
            Theme theme = App.STORAGE.theme();
            for (Field declaredField : getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object obj = declaredField.get(this);
                if (obj instanceof ThemeUpdatable updatable) {
                    updatable.theme(theme);
                }
            }
        } catch (IllegalAccessException ignored) {}
    }
}
