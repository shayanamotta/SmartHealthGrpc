package com.shayana;

import com.jfoenix.controls.JFXListView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {


    @FXML
    public GridPane mainGrid;

    @FXML
    public JFXListView statusList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusList.setItems(Utils.statusList);
        Utils.statusList.addListener((ListChangeListener<String>) c -> statusList.refresh());

    }

    @FXML
    public void showPriceLists() throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("pricelist.fxml"))));
        stage.setTitle("Price Lists");
        stage.show();
    }

    @FXML
    public void showAppointments(MouseEvent mouseEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("appointments.fxml"))));
        stage.setTitle("Appointments");
        stage.show();
    }

    public void showEmployees(MouseEvent mouseEvent) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("employees.fxml"))));
        stage.setTitle("Employees");
        stage.show();
    }
}
