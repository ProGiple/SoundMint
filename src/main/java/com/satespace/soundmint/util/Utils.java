package com.satespace.soundmint.util;

import lombok.experimental.UtilityClass;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

@UtilityClass
public class Utils {
    public AudioFileFormat getAudioFileFormat(File file) {
        try {
            return AudioSystem.getAudioFileFormat(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println("Ошибка извлечения обложки: " + e.getMessage());
        }
        return null;
    }
}
