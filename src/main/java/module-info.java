module com.example.smarthealthgrpc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shayana.smarthealthgrpc to javafx.fxml;
    exports com.shayana.smarthealthgrpc;
}