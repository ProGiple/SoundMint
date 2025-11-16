package com.satespace.soundmint.items.main;

import com.satespace.soundmint.items.abs.CollectionPane;
import com.satespace.soundmint.items.modals.abs.ModalWrapper;
import com.satespace.soundmint.items.modals.playlist.PlaylistModal;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.util.Utils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

@Getter
public class PlaylistPane extends CollectionPane {
    private final Playlist playlist;
    public PlaylistPane(Playlist playlist) {
        super(playlist.getMetaObject().getArtwork());
        this.playlist = playlist;
        this.getStyleClass().add("collection-pane");
    }

    public PlaylistPane() {
        this(new Playlist());
    }

    @Override
    public void onClick(MouseEvent event) {
        PlaylistModal modal = new PlaylistModal(this.playlist);
        ModalWrapper<PlaylistModal> modalWrapper = new ModalWrapper<>(modal);

        Bounds bounds = modalWrapper.localToScene(this.getBoundsInLocal());
        Point2D local = modalWrapper.sceneToLocal(
                bounds.getMinX() + bounds.getWidth() / 2,
                bounds.getMinY() + bounds.getHeight() / 2
        );

        modalWrapper.show(local.getX(), local.getY());
    }
}
