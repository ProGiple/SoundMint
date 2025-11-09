module com.satespace.soundmint {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.media;
    requires jaudiotagger;
    requires java.compiler;
    requires javafx.base;

    opens com.satespace.soundmint to javafx.fxml;
    exports com.satespace.soundmint;
    exports com.satespace.soundmint.items.main.musicButtons;
    exports com.satespace.soundmint.musix;
    opens com.satespace.soundmint.musix to javafx.fxml;
    exports com.satespace.soundmint.musix.meta;
    opens com.satespace.soundmint.musix.meta to javafx.fxml;
    exports com.satespace.soundmint.musix.playlist;
    opens com.satespace.soundmint.musix.playlist to javafx.fxml;
    exports com.satespace.soundmint.musix.track;
    opens com.satespace.soundmint.musix.track to javafx.fxml;
    exports com.satespace.soundmint.items.abs;
    exports com.satespace.soundmint.controllers;
    opens com.satespace.soundmint.controllers to javafx.fxml;
    exports com.satespace.soundmint.items.main;
}