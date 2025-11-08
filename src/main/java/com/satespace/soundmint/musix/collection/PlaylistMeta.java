package com.satespace.soundmint.musix.collection;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.meta.Meta;
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
}
