package com.shayana;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PriceList implements Initializable {
    public JFXTextField serviceIdInput;
    public JFXTextField slotsInput;
    public JFXTimePicker timeOffered;
    public JFXTextField serviceCostInput;
    public JFXTextField serviceNameInput;
    public TableColumn serviceId;
    public TableColumn serviceName;
    public TableColumn timeoffered;
    public TableColumn cost;
    public TableColumn slots;
    public TableView serviceList;
    public JFXListView eventsList;
    public JFXButton removeServiceBtn;

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

        eventsList.setItems(Utils.priceListEvents);
        Utils.priceListEvents.addListener((ListChangeListener<String>) c -> {
         eventsList.refresh();
        });
        serviceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removeServiceBtn.setVisible(true);
        });
    }

    public void addService() {
        var sId=serviceIdInput.getText();
        var sname=serviceNameInput.getText();
        var scost=serviceCostInput.getText();
        var stime=timeOffered.getEditor().getText();
        var sslots=slotsInput.getText();
        if (sId.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the service ID");
            return;
        }

        if (sname.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the service name");
            return;
        }
        if (scost.isEmpty() || !isNumeric(scost.trim())){
            JOptionPane.showMessageDialog(null,"Invalid service cost. please provide a numeric value");
            return;
        }
        if (stime.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the time for the service");
            return;
        }
        if (sslots.isEmpty() || !isNumeric(sslots.trim())){
            JOptionPane.showMessageDialog(null,"Please provide the number of slots available for the service");
            return;
        }
        Service service= Service.newBuilder()
                .setServiceId(sId)
                .setSlots(Integer.parseInt(sslots))
                .setServiceName(sname)
                .setCost(Integer.parseInt(scost))
                .setTimeoffered(stime)
                .build();
        new Client().addService(service);

    }
    private boolean isNumeric(String number){
        try {
            Integer.parseInt(number);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public void removeService() {
        var selectedId=((Service)serviceList.getSelectionModel().getSelectedItems().get(0)).getServiceId();
        new Client().removeService(selectedId);
    }
}
