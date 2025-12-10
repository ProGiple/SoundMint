package com.satespace.soundmint.items.main;

import com.satespace.soundmint.items.abs.CollectionPane;
import com.satespace.soundmint.items.modals.abs.Modal;
import com.satespace.soundmint.items.modals.abs.ModalWrapper;
import com.satespace.soundmint.items.modals.playlist.PlaylistModal;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.util.Utils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

@Getter
public class PlaylistPane extends CollectionPane {
    private final Playlist playlist;
    public PlaylistPane(Playlist playlist) {
        super(playlist.getMetaObject().getArtwork());
        this.playlist = playlist;
        this.getStyleClass().add("collection-pane");

        Tooltip tooltip = new Tooltip("Плейлист: " + playlist.getMetaObject().getName());
        Tooltip.install(this, tooltip);
    }

    public PlaylistPane() {
        this(new Playlist());
    }

    @Override
    public void onClick(MouseEvent event) {
        Modal.open(new PlaylistModal(this.playlist), this);
    }
}
