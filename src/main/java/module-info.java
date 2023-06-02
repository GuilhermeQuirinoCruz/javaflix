module main {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.sql;
    requires java.base;

//    opens main to javafx.fxml;
//    opens model to javafx.fxml, javafx.base;
//    opens view to javafx.fxml;
//    opens controller to javafx.fxml;
    opens main;
    opens model;
    opens view;
    opens controller;
    exports main;
}
