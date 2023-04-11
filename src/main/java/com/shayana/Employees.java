package com.shayana;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Employees implements Initializable {

    public JFXTextField employeeIdInput;
    public JFXTextField employeeNameInput;
    public JFXListView<String> eventsList;
    public TableColumn<EmployeeItem,String> employeeId;
    public TableColumn<EmployeeItem,String> employeeName;
    public TableView<EmployeeItem> serviceList;
    public JFXButton deleteEmployee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteEmployee.setVisible(false);

        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        serviceList.setItems(Utils.employeeItems);
        Utils.employeeItems.addListener((ListChangeListener<EmployeeItem>) c -> serviceList.refresh());
        new Client().getEmployees();

        eventsList.setItems(Utils.employeeEvents);
        Utils.employeeEvents.addListener((ListChangeListener<String>) c -> {
            eventsList.refresh();
        });
        serviceList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            deleteEmployee.setVisible(true);
        });
    }

    public void deleteEmployee(ActionEvent event) {
        var selectedId=serviceList.getSelectionModel().getSelectedItems().get(0).getEmployeeId();
        new Client().deleteEmployees(DeleteEmployeeRequest.newBuilder()
                .setEmployeeId(selectedId).build());
    }

    public void addService() {
        var name=employeeNameInput.getText();
        var id=employeeIdInput.getText();
        System.out.println(name+id);
        if (name.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the employee name");
            return;
        }

        if (id.isEmpty()){
            JOptionPane.showMessageDialog(null,"Please provide the employee ID");
            return;
        }
        new Client().addEmployee(AddEmployeeRequest.newBuilder()
                .setEmployee(EmployeeItem.newBuilder()
                        .setEmployeeId(id)
                        .setEmployeeName(name).build()).build());
    }
}
