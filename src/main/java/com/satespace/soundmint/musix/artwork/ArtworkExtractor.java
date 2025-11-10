package com.satespace.soundmint.musix.artwork;

import com.satespace.soundmint.SourceImage;
import javafx.scene.image.Image;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import java.io.ByteArrayInputStream;
import java.io.File;

@UtilityClass
public class ArtworkExtractor {
    public Image extractArtwork(File file) {
        Image artwork;

        artwork = file == null ? null : getEmbeddedArtwork(file);
        if (artwork == null) {
            artwork = SourceImage.DEFAULT_TRACK_ARTWORK.asImage();
        }

        return artwork;
    }

    @SneakyThrows
    private Image getEmbeddedArtwork(File file) {
        AudioFile audioFile = AudioFileIO.read(file);
        try {
            return new Image(new ByteArrayInputStream(audioFile.getTag().getFirstArtwork().getBinaryData()));
        } catch (NullPointerException e) {
            return null;
        }
    }
}
