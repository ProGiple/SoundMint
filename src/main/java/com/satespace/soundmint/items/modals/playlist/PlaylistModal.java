package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.controllers.AppController;
import com.satespace.soundmint.items.modals.abs.Modal;
import com.satespace.soundmint.musix.playlist.Playlist;
import javafx.scene.layout.StackPane;
import lombok.Getter;

@Getter
public class PlaylistModal extends Modal {
    private final Playlist playlist;
    public PlaylistModal(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean showed(AppController controller) {
        return controller.getOpenedModals(
                PlaylistModal.class,
                m -> m.getModal().getPlaylist().equals(this.playlist))
                .findFirst()
                .isPresent();
    }
}
