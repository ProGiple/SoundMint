package com.satespace.soundmint.items.modals.playlist;

import com.satespace.soundmint.musix.playlist.Playlist;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PlaylistImage extends Pane {
    public PlaylistImage(Playlist playlist) {
        ImageView imageView = new ImageView(playlist.getMetaObject().getArtwork());
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(this.heightProperty().subtract(10));

        StackPane stackPane = new StackPane(imageView);
        stackPane.setAlignment(Pos.CENTER);
        this.getChildren().add(stackPane);
    }
}
