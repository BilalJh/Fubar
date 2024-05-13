module net.bilaljh.fubar {
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
    requires javafx.graphics;
    requires annotations;
    requires java.desktop;
    requires javafx.media;

    opens net.bilaljh.fubar to javafx.fxml;
    exports net.bilaljh.fubar;
}