package com.shayana;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Appointments implements Initializable {
    public JFXTextField clientName;
    public JFXButton checkAvailable;
    public JFXButton cancelBooking;
    public TableView<Service> serviceList;
    public TableColumn<Service,String> serviceId;
    public TableColumn<Service,String> serviceName;
    public TableColumn<Service,String> timeoffered;
    public TableColumn<Service,String> cost;
    public TableColumn<Service,String> slots;
    public TableColumn<Booking,String> clientId;
    public TableColumn<Booking,String> serviceBId;
    public JFXListView<String> eventsList;
    public TableView<Booking> bookingList;
    public JFXButton bookAppointment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceId.setCellValueFactory(new PropertyValueFactory<Service, String>("serviceId"));
        serviceName.setCellValueFactory(new PropertyValueFactory<Service, String>("serviceName"));
        timeoffered.setCellValueFactory(new PropertyValueFactory<Service, String>("timeoffered"));
        cost.setCellValueFactory(new PropertyValueFactory<Service, String>("cost"));
        slots.setCellValueFactory(new PropertyValueFactory<Service, String>("slots"));

        serviceList.setItems(Utils.serviceList);
        Utils.serviceList.addListener((ListChangeListener<Service>) c -> serviceList.refresh());
        new Client().getServices();
        bookAppointment.setVisible(false);
        serviceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            bookAppointment.setVisible(true);
            checkAvailable.setVisible(true);
        });

        clientId.setCellValueFactory(new PropertyValueFactory<Booking, String>("clientId"));
        serviceBId.setCellValueFactory(new PropertyValueFactory<Booking, String>("serviceBId"));

        bookingList.setItems(Utils.bookingsList);
        Utils.bookingsList.addListener((ListChangeListener<Booking>) c -> bookingList.refresh());
        bookingList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> cancelBooking.setVisible(true));

        eventsList.setItems(Utils.appointmentEvents);
        Utils.appointmentEvents.addListener((ListChangeListener<String>) c -> eventsList.refresh());

    }

    public void bookAppointment() {
var clientId=clientName.getText();
        if (clientId.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the client Name");
            return;
        }
  var selectedId= serviceList.getSelectionModel().getSelectedItems().get(0).getServiceId();
        if (selectedId==null){
            JOptionPane.showMessageDialog(null,"Please select a service first");
            return;
        }
        new Client().bookAppointMent(AppointmentRequest.newBuilder()
                .setClientId(clientId)
                .setServiceId(selectedId)
                .build());
    }

    public void cancelBooking() {
        var selected= bookingList.getSelectionModel().getSelectedItems().get(0);
        if (selected==null){
            JOptionPane.showMessageDialog(null,"Please select a booking first");
            return;
        }
        new Client().cancelBooking(CancellationRequest.newBuilder()
                .setClientId(selected.getClientId())
                .setServiceId(selected.getServiceBId()));
    }

    public void chekAvailability() {
        var selectedId= serviceList.getSelectionModel().getSelectedItems().get(0).getServiceId();
        if (selectedId==null){
            JOptionPane.showMessageDialog(null,"Please select a service first");
            return;
        }
        new Client().checkServiceAvailable(CheckAvailabilityRequest.newBuilder()
                .setServiceId(selectedId).build());

    }
}
