package com.satespace.soundmint.musix.meta;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;

@Getter @Setter
public class TrackMeta implements Meta {
    private String name;
    private String artist = "Неизвестный исполнитель";
    private String album = "Не указан";
    private LocalDateTime releaseDate = null;
    private String genre = "Не указан";

    public TrackMeta(File file) {
        this.name = file.getName();
    }
}
