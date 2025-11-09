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
        String title = tag.getFirst(FieldKey.TITLE);
        if (!title.isEmpty()) info.setName(title);

        String artist = tag.getFirst(FieldKey.ARTIST);
        if (!artist.isEmpty()) info.setArtist(artist);

        String album = tag.getFirst(FieldKey.ALBUM);
        if (!album.isEmpty()) info.setAlbum(album);

        String genre = tag.getFirst(FieldKey.GENRE);
        if (!genre.isEmpty()) info.setGenre(genre);
    }
}
