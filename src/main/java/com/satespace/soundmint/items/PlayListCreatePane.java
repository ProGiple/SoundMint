package com.satespace.soundmint.items;

import com.satespace.soundmint.App;
import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.collection.Playlist;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;

public class PlayListCreatePane extends Pane {
    public static final double IMAGE_OPACITY = 0.2;
    public static final int IMAGE_FIT_HEIGHT = 50;
    public static final double ON_HOVER_PANES_OPACITY = 0.75;
    public static final int ON_HOVER_PANES_PAUSE_MILLIS = 50;

    private boolean isHovered = false;
    public PlayListCreatePane() {
        this.getStyleClass().addAll("playlist-base-pane", "create-playlist-pane");

        Image image = SourceImage.CREATE_PLAYLIST_BUTTON.asImage();
        ImageView imageView = new ImageView(image);
        imageView.setOpacity(IMAGE_OPACITY);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(IMAGE_FIT_HEIGHT);

        StackPane stackPane = new StackPane(imageView);
        stackPane.prefHeightProperty().bind(this.heightProperty());
        stackPane.prefWidthProperty().bind(this.widthProperty());
        stackPane.setAlignment(Pos.CENTER);
        this.getChildren().add(stackPane);

        HBox.setMargin(this, new Insets(0, 20, 0, 10));
        this.setOnMouseClicked(e -> {
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
        });

        this.setOnMouseEntered(e -> {
            isHovered = true;
            this.playOpacityAnimation();
        });
        this.setOnMouseExited(e -> {
            isHovered = false;
            this.playOpacityAnimation();
        });
    }

    private void playOpacityAnimation() {
        List<PlaylistPane> list = App.CONTROLLER.getTopPlayListBlock().getPanes();
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
}
