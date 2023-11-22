module fr.insalyonif.hubert {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens fr.insalyonif.hubert to javafx.fxml;
    exports fr.insalyonif.hubert.controller;
    opens fr.insalyonif.hubert.controller to javafx.fxml;
    exports fr.insalyonif.hubert.views;
    opens fr.insalyonif.hubert.views to javafx.fxml;
}