module com.senurah.lab_test {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.senurah.lab_test to javafx.fxml;
    exports com.senurah.lab_test;
}