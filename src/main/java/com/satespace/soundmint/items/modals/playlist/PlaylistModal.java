package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.controllers.AppController;
import com.satespace.soundmint.items.modals.abs.Modal;
import com.satespace.soundmint.musix.playlist.Playlist;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import lombok.Getter;

@Getter
public class PlaylistModal extends Modal {
    private final Playlist playlist;
    public PlaylistModal(Playlist playlist) {
        super(0.5);
        this.playlist = playlist;

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        this.getRowConstraints().add(rowConstraints);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(60);
        this.getColumnConstraints().add(columnConstraints);

        this.add(new TrackPlaylistScroller(playlist), 0, 0);
        //this.add(new PlaylistImage(playlist), 1, 0);
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
