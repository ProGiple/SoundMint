package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.musix.track.Track;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;

public class NextMusicButton extends AbsMusicButton {
    public NextMusicButton() {
        super(SourceImage.NEXT_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (isAllowed()) {
            environment.playNext();
        }
    }

    @Override
    protected boolean isAllowed() {
        return App.STORAGE.activeTrackEnvironment().getNext() != null;
    }

    @Override
    public void updateState() {
        super.updateState();

        Track next = App.STORAGE.activeTrackEnvironment().getNext();
        activedTooltip.setText(next == null ?
                "Воспроизведение недоступно" :
                "Следующий трек: " + next.getMetaObject().getName());
    }
}
