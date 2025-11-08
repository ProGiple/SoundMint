package com.satespace.soundmint;

import com.satespace.soundmint.items.PlayListCreatePane;
import com.satespace.soundmint.items.PlaylistPane;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AppController {
    @FXML protected HBox topPlayListBlock;
    @FXML protected ScrollPane playListScroller;

    public void initialize() {
        for (int i = 0; i < App.STORAGE.playlists().size(); i++) {
            PlaylistPane pane = new PlaylistPane(App.STORAGE.playlists().get(i));
            this.topPlayListBlock.getChildren().add(pane);
        }

        this.topPlayListBlock.setAlignment(Pos.CENTER);
        this.topPlayListBlock.getChildren().add(new PlayListCreatePane());

        this.playListScroller.setOnScroll(event -> {
            double delta = event.getDeltaY() * 0.001;
            this.playListScroller.setHvalue(Math.max(0, Math.min(1, this.playListScroller.getHvalue() - delta)));
        });
    }
}