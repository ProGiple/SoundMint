package com.satespace.soundmint.musix.artwork;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.util.Utils;
import javafx.scene.image.Image;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.AudioFileFormat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

@UtilityClass
public class ArtworkExtractor {
    public Image extractArtwork(File file) {
        Image artwork;

        artwork = getEmbeddedArtwork(file);

        if (artwork == null) {
            artwork = SourceImage.PLAYLIST_DEFAULT_IMAGE.asImage();
        }

        return artwork;
    }

    private Image getEmbeddedArtwork(File audioFile) {

        AudioFileFormat fileFormat = Utils.getAudioFileFormat(audioFile);
        if (fileFormat != null) {
            Map<String, Object> properties = fileFormat.properties();
            System.out.println(properties.keySet());

            if (properties.containsKey("image")) {
                Object imageData = properties.get("image");

                if (imageData instanceof byte[] imageBytes) {
                    return new Image(new ByteArrayInputStream(imageBytes));
                }
            }
        }

        return null;
    }
}
