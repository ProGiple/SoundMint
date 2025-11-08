module com.satespace.soundmint {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.media;
    requires jaudiotagger;

    opens com.satespace.soundmint to javafx.fxml;
    exports com.satespace.soundmint;
    exports com.satespace.soundmint.items;
    exports com.satespace.soundmint.items.musicButtons;
    exports com.satespace.soundmint.musix;
    opens com.satespace.soundmint.musix to javafx.fxml;
    exports com.satespace.soundmint.musix.meta;
    opens com.satespace.soundmint.musix.meta to javafx.fxml;
    exports com.satespace.soundmint.musix.collection;
    opens com.satespace.soundmint.musix.collection to javafx.fxml;
    exports com.satespace.soundmint.musix.track;
    opens com.satespace.soundmint.musix.track to javafx.fxml;
}