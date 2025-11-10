package com.satespace.soundmint.items.main.musicButtons;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.track.ActiveTrackEnvironment;
import com.satespace.soundmint.musix.track.Track;
import javafx.event.ActionEvent;

public class PreviousMusicButton extends AbsMusicButton {
    public PreviousMusicButton() {
        super(SourceImage.PREVIOUS_TRACK_BUTTON);
        this.updateState();
    }

    @Override
    public void onClick(ActionEvent event) {
        ActiveTrackEnvironment environment = App.STORAGE.activeTrackEnvironment();
        if (isAllowed()) {
            environment.playPrevious();
        }
    }

    @Override
    protected boolean isAllowed() {
        System.out.println(App.STORAGE.activeTrackEnvironment().getPrevious());
        return App.STORAGE.activeTrackEnvironment().getPrevious() != null;
    }

    @Override
    public void updateState() {
        super.updateState();

        Track previous = App.STORAGE.activeTrackEnvironment().getPrevious();
        activedTooltip.setText(previous == null ?
                "Воспроизведение недоступно" :
                "Предыдущий трек: " + previous.getMetaObject().getName());
    }
}
