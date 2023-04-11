package com.shayana;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utils {
public static ObservableList<String> statusList= FXCollections.observableArrayList();
    public static ObservableList<Service> serviceList= FXCollections.observableArrayList();
    public static ObservableList<Booking> bookingsList= FXCollections.observableArrayList();
    public static ObservableList<String> priceListEvents= FXCollections.observableArrayList();
    public static ObservableList<String> appointmentEvents= FXCollections.observableArrayList();

    public static ObservableList<EmployeeItem> employeeItems= FXCollections.observableArrayList();
    public static ObservableList<String> employeeEvents= FXCollections.observableArrayList();

}
