package com.satespace.soundmint.musix.playlist;

import com.satespace.soundmint.musix.meta.MetaContainer;
import com.satespace.soundmint.musix.meta.PlaylistMeta;
import com.satespace.soundmint.musix.track.Track;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist extends MetaContainer<PlaylistMeta> {
    private final List<Track> trackList;
    @Setter
    private PlaybackMode playbackMode = PlaybackMode.SEQUENTIAL;

    public Playlist(PlaylistMeta meta, PlaybackMode playbackMode) {
        super(meta);
        this.trackList = new ArrayList<>();
        this.playbackMode = playbackMode;
    }

    public Playlist() {
        super(new PlaylistMeta());
        this.trackList = new ArrayList<>();
    }

    public void addTrack(Track track) {
        this.trackList.add(track);
    }

    public void removeTrack(Track track) {
        this.trackList.remove(track);
    }

    /**
     * Получает трек по индексу
     */
    public Track getTrack(int index) {
        if (index >= 0 && index < trackList.size()) {
            return trackList.get(index);
        }
        return null;
    }

    /**
     * Получает индекс трека в плейлисте
     */
    public int getTrackIndex(Track track) {
        return trackList.indexOf(track);
    }

    /**
     * Проверяет, содержится ли трек в плейлисте
     */
    public boolean containsTrack(Track track) {
        return trackList.contains(track);
    }

    /**
     * Получает количество треков в плейлисте
     */
    public int getTrackCount() {
        return trackList.size();
    }
}
