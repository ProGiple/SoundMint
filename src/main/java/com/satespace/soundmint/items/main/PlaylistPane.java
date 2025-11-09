package com.satespace.soundmint.items.main;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.items.abs.CollectionPane;
import com.satespace.soundmint.musix.playlist.Playlist;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

@Getter
public class PlaylistPane extends CollectionPane {
    private final Playlist playlist;
    public PlaylistPane(Playlist playlist) {
        super(playlist.getMetaObject().getArtwork() == null ?
                SourceImage.PLAYLIST_DEFAULT_IMAGE.asImage() :
                playlist.getMetaObject().getArtwork());
        this.playlist = playlist;
        this.getStyleClass().add("collection-pane");
    }

    public PlaylistPane() {
        this(new Playlist());
    }

    @Override
    public void onClick(MouseEvent event) {
        System.out.println("Clicked");
    }
}
