module main {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shayana to javafx.fxml;
    exports com.shayana;
}