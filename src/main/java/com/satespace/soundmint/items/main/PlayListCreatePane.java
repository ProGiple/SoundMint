package com.satespace.soundmint.items.main;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.items.abs.Clickable;
import com.satespace.soundmint.items.abs.CollectionPane;
import com.satespace.soundmint.items.abs.animations.RotateScalingAnimated;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.util.Utils;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class PlayListCreatePane extends Pane implements Clickable<MouseEvent>, RotateScalingAnimated {
    public static final double IMAGE_OPACITY = 0.2;
    public static final int IMAGE_FIT_HEIGHT = 25;
    public static final double ON_HOVER_PANES_OPACITY = 0.65;
    public static final int ON_HOVER_PANES_PAUSE_MILLIS = 50;

    private boolean isHovered = false;
    public PlayListCreatePane() {
        this.getStyleClass().addAll("mini-collection-base-pane", "create-playlist-pane");

        Image image = SourceImage.CREATE_PLAYLIST_BUTTON.asImage();
        StackPane stackPane = Utils.setNodeImage(this, image, IMAGE_OPACITY, IMAGE_FIT_HEIGHT);
        this.getChildren().add(stackPane);

        this.setOnMouseClicked(this::onClick);

        this.setOnMouseEntered(e -> {
            isHovered = true;
            this.playOpacityAnimation();
            this.playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER, this);
        });

        this.setOnMouseExited(e -> {
            isHovered = false;
            this.playOpacityAnimation();
            this.playAnimation(0, 1.0, this);
        });
    }

    private void playOpacityAnimation() {
        List<CollectionPane> list = App.CONTROLLER.getTopPlayListBlock().getPanes();
        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            this.playOpacityAnimation(node, ON_HOVER_PANES_PAUSE_MILLIS * i);
        }
    }

    private void playOpacityAnimation(Node node, int pauseMillis) {
        PauseTransition delay = new PauseTransition(Duration.millis(pauseMillis));
        delay.setOnFinished(e -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(175), node);
            fadeTransition.setToValue(this.isHovered ? ON_HOVER_PANES_OPACITY : 1.0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();
        });
        delay.play();
    }

    @Override
    public void onClick(MouseEvent event) {
        Playlist playlist = new Playlist();
        App.STORAGE.playlists().add(playlist);

        PlaylistPane pane = App.CONTROLLER.createPlaylistPane(playlist);
        pane.setScaleX(0);
        pane.setScaleY(0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(225), pane);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();

        this.playOpacityAnimation(pane, 0);
    }
}
