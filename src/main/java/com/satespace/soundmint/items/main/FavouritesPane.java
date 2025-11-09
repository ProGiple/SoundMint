package com.satespace.soundmint.items.main;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.items.abs.CollectionPane;
import javafx.scene.input.MouseEvent;

public class FavouritesPane extends CollectionPane {
    public FavouritesPane() {
        super(SourceImage.FAVOURITES_LIST_IMAGE.asImage());
        this.getStyleClass().add("collection-pane");
    }

    @Override
    public void onClick(MouseEvent event) {

    }
}
