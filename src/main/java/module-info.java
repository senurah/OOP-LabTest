module com.senurah.lab_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.senurah.lab_test to javafx.fxml;
    exports com.senurah.lab_test;
    exports com.senurah.lab_test.ui to javafx.graphics;
    exports com.senurah.lab_test.config;
    exports com.senurah.lab_test.core;
    exports com.senurah.lab_test.logging;
    exports com.senurah.lab_test.threads;
}
