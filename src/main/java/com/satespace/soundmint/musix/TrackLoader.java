package com.satespace.soundmint.musix;

import com.satespace.soundmint.musix.track.Track;
import com.satespace.soundmint.util.Utils;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Map;

@UtilityClass
public class TrackLoader {
    public void loadTrack(File file) {

        TrackMeta meta = new TrackMeta(file);

        AudioFileFormat audioFileFormat = Utils.getAudioFileFormat(file);
        if (audioFileFormat != null) {
            Map<String, Object> properties = audioFileFormat.properties();
            int duration = calculateDuration(audioFileFormat, file);

            extractMetadata(properties, meta);

            Track simpleTrack = new Track(meta, file, duration);
        }
    }


    private int calculateDuration(AudioFileFormat fileFormat, File audioFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();

            long frames = audioStream.getFrameLength();
            double durationInSeconds = (frames) / format.getFrameRate();

            audioStream.close();
            return (int) durationInSeconds;

        } catch (Exception e) {
            Map<String, Object> properties = fileFormat.properties();
            if (properties.containsKey("duration")) {
                int microseconds = (Integer) properties.get("duration");
                return microseconds / 1000000;
            }
        }
        return 0;
    }

    private void extractMetadata(Map<String, Object> properties, TrackMeta info) {
        if (properties.containsKey("title")) {
            info.setName((String) properties.get("title"));
        }
        if (properties.containsKey("author")) {
            info.setArtist((String) properties.get("author"));
        }
        if (properties.containsKey("album")) {
            info.setAlbum((String) properties.get("album"));
        }
        if (properties.containsKey("genre")) {
            info.setGenre((String) properties.get("genre"));
        }
//        if (properties.containsKey("year")) {
//            Object year = properties.get("year");
//            if (year instanceof String) {
//                try {
//                    info.setReleaseDate(Integer.parseInt(((String) year).trim()));
//                } catch (NumberFormatException e) {
//                    // Игнорируем нечисловые значения
//                }
//            }
//        }

        // Альтернативные названия тегов
        if (info.getName() == null && properties.containsKey("dc:title")) {
            info.setName((String) properties.get("dc:title"));
        }
        if (info.getArtist() == null && properties.containsKey("artist")) {
            info.setArtist((String) properties.get("artist"));
        }
    }
}
