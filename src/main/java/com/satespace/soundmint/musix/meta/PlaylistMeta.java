package com.satespace.soundmint.musix.meta;

import com.satespace.soundmint.SourceImage;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistMeta implements Meta {
    private String name;
    private Image artwork;

    public PlaylistMeta() {
//        int playlistsAmount = App.STORAGE.playlists().size();
        this.name = "Плейлист " + 1;
        this.artwork = SourceImage.PLAYLIST_DEFAULT_IMAGE.asImage();
    }

    public PlaylistMeta(String name, Image image) {
        this.name = name;
        this.artwork = image;
    }
}
