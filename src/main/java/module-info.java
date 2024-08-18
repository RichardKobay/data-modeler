module edu.upvictoria.datamodeler {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens edu.upvictoria.datamodeler to javafx.fxml;
    exports edu.upvictoria.datamodeler;
    exports edu.upvictoria.datamodeler.controllers to javafx.fxml;
    opens edu.upvictoria.datamodeler.controllers to javafx.fxml;
}