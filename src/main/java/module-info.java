module edu.cotarelo.videoplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens edu.cotarelo.videoplayer to javafx.fxml;
    exports edu.cotarelo.videoplayer;
}
