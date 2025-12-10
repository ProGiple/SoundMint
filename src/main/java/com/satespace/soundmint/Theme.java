package com.satespace.soundmint;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum Theme {
    PRIMARY("cd2cf5"),
    ORANGE("f5c92c"),
    GREEN("2ff52c"),
    BLUE("2c8af5"),
    WHITE("ffffff"),
    RED("f52c39");

    private final String hex;
}
