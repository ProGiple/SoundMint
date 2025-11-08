package com.satespace.soundmint.items;

import com.satespace.soundmint.SourceImage;
import com.satespace.soundmint.musix.collection.Playlist;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;

@Getter
public class PlaylistPane extends Pane {
    public static final int ROTATE_ANGLE = 5;
    public static final double SIZE_MULTIPLIER = 0.9;
    public static final int IMAGE_FIT_HEIGHT = 40;
    public static final double IMAGE_OPACITY = 0.2;

    private final Playlist playlist;
    public PlaylistPane(Playlist playlist) {
        this.playlist = playlist;
        this.getStyleClass().addAll("playlist-pane", "playlist-base-pane");

        Image image = playlist.getImage() == null ? SourceImage.PLAYLIST_DEFAULT_IMAGE.asImage() : playlist.getImage();
        ImageView imageView = new ImageView(image);
        imageView.setOpacity(IMAGE_OPACITY);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(IMAGE_FIT_HEIGHT);

        StackPane stackPane = new StackPane(imageView);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.prefWidthProperty().bind(this.widthProperty());
        stackPane.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().add(stackPane);

        this.setOnMouseClicked(e -> System.out.println("Clicked"));
        this.setOnMouseEntered(e -> playAnimation(ROTATE_ANGLE, SIZE_MULTIPLIER));
        this.setOnMouseExited(e -> playAnimation(0, 1.0));
    }

    public PlaylistPane() {
        this(new SimplePlaylist());
    }

    private void playAnimation(int rotateAngle, double sizeMultiplier) {
        RotateTransition rt = new RotateTransition(Duration.millis(250), this);
        ScaleTransition st = new ScaleTransition(Duration.millis(150), this);

        rt.setToAngle(rotateAngle);
        st.setToX(sizeMultiplier);
        st.setToY(sizeMultiplier);

        rt.play();
        st.play();
    }
}
