package com.satespace.soundmint.musix;

import com.satespace.soundmint.musix.track.Track;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

@UtilityClass
public class TrackLoader {

    @SneakyThrows
    public Track loadTrack(File file) {

        TrackMeta meta = new TrackMeta(file);

        AudioFile audioFile = AudioFileIO.read(file);
        if (audioFile != null) {
            Tag audioTag = audioFile.getTag();
            AudioHeader header = audioFile.getAudioHeader();

            double duration = header.getPreciseTrackLength();

            extractMetadata(audioTag, meta);

            return new Track(meta, file, duration);
        }
        return null;
    }



    private void extractMetadata(Tag tag, TrackMeta info) {

        info.setName(tag.getFirst(FieldKey.TITLE));
        info.setArtist(tag.getFirst(FieldKey.ARTIST));
        info.setAlbum(tag.getFirst(FieldKey.ALBUM));
        info.setGenre(tag.getFirst(FieldKey.GENRE));
    }
}
