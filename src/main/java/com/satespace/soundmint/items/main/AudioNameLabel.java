package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.items.abs.MintLabel;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class AudioNameLabel extends MintLabel {
    public AudioNameLabel() {
        this.setTooltip((String) null);
    }

    @Override
    public void onClick(MouseEvent event) {

    }

    @Override
    public void setTooltip(String value) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();

        MediaPlayer player = environment.getMediaPlayer();
        if (environment.getActiveTrack() == null || player == null || player.getStatus() == MediaPlayer.Status.DISPOSED) {
            if (this.getPlacedTooltip() != null) Tooltip.uninstall(this, this.getPlacedTooltip());
            return;
        }
        String name = environment.getActiveTrack().getMetaObject().getName();
        super.setTooltip("Полное название: " + name);
    }
}
