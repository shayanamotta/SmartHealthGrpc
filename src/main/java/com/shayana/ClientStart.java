package com.shayana;

import io.grpc.stub.StreamObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientStart extends Application {
    public static EmployeeStreamer streamer;
    public static void main(String[] args) {
        launch(args);
    }

    public static void registerStreamer(StreamObserver<ListEmployeeResponse> responseObserver){
        streamer=new EmployeeStreamer(responseObserver);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
