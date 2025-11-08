module com.satespace.soundmint {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;

    opens com.satespace.soundmint to javafx.fxml;
    exports com.satespace.soundmint;
    exports com.satespace.soundmint.items;
    exports com.satespace.soundmint.musix;
    opens com.satespace.soundmint.musix to javafx.fxml;
}