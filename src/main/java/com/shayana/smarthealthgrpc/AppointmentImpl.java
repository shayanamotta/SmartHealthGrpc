package com.shayana.smarthealthgrpc;

<<<<<<< HEAD
import io.grpc.stub.StreamObserver;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;

public class AppointmentImpl extends AppointmentGrpc.AppointmentImplBase {
    @Override
    public void makeAppointment(AppointmentRequest request, StreamObserver<SmartResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" book appointment requested");


        try {
            Connection connection= org.shayana.Connection.connect();
            var booked=checkBookedSlots(request.getServiceId(),connection);
            var available=getAvailableSlots(request.getServiceId(),connection);
            System.out.println(booked);
            if (available<=booked || booked+1>available){
                responseObserver.onNext(SmartResponse.newBuilder()
                                .setMessage("The service is fully booked")
                                .setSuccess(false)
                                .setIsAvailable(false)
                        .build());
                responseObserver.onCompleted();
            }
            else {
                var sql="INSERT INTO Appointments (ServiceID,slots,clientId) VALUES (?,?,?)";
                var statement=connection.prepareStatement(sql);
                statement.setString(1,request.getServiceId());
                statement.setInt(2,1);
                statement.setString(3,request.getClientId());
                statement.execute();
                statement.closeOnCompletion();
                Booking booking=new Booking();
                booking.setClientId(request.getClientId());
                booking.setServiceBId(request.getServiceId());
                connection.close();
               Utils.bookingsList.add(booking);
                responseObserver.onNext(SmartResponse.newBuilder()
                                .setSuccess(true)
                                .setMessage("Service booked")
                                .setIsAvailable(true)
                        .build());
                responseObserver.onCompleted();
            }


        }catch (SQLException e){
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    private int getAvailableSlots(String serviceId, Connection connection){
        try {
            var sql="SELECT slots FROM Services WHERE ServiceID=? LIMIT 1";
            var statement=connection.prepareStatement(sql);
            statement.setString(1,serviceId);
            var results=statement.executeQuery();
            var available=results.getInt(1);
            statement.closeOnCompletion();
            return available;

        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    private int checkBookedSlots(String serviceId, Connection connection){
        try {
            var sql="SELECT slots FROM Appointments WHERE ServiceID=?";
            var statement=connection.prepareStatement(sql);
            statement.setString(1,serviceId);
            var results=statement.executeQuery();
            var booked=0;
            while (results.next()){
                booked+=results.getInt(1);
            }
            statement.closeOnCompletion();
            return booked;

        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void cancelAppointment(CancellationRequest request, StreamObserver<SmartResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" cancel appointment requested");

        try {
            Connection connection= org.shayana.Connection.connect();
            var sql="DELETE FROM Appointments WHERE clientId=? AND ServiceID=?";
            var statement=connection.prepareStatement(sql);
            statement.setString(1,request.getClientId());
            statement.setString(2,request.getServiceId());
            statement.execute();
            statement.closeOnCompletion();
            Utils.bookingsList.removeIf(item-> Objects.equals(item.getServiceBId(), request.getServiceId()) && Objects.equals(item.getClientId(), request.getClientId()));
            responseObserver.onNext(SmartResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Appointment Cancelled")
                    .build());
            connection.close();
            responseObserver.onCompleted();

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    @Override
    public void checkAvailability(CheckAvailabilityRequest request, StreamObserver<SmartResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" check service available requested");

        Connection connection= org.shayana.Connection.connect();
        var slots=getAvailableSlots(request.getServiceId(),connection);
        var booked=checkBookedSlots(request.getServiceId(),connection);
        try {
            connection.close();
        } catch (SQLException e) {
            responseObserver.onError(e);
        }
        if (slots<=booked || booked+1>slots){
            responseObserver.onNext(SmartResponse.newBuilder()
                    .setMessage("The service is fully booked")
                    .setSuccess(false)
                    .setIsAvailable(false)
                    .build());
            responseObserver.onCompleted();
        }else {
            responseObserver.onNext(SmartResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Service available")
                    .setIsAvailable(true)
                    .build());
            responseObserver.onCompleted();
        }
    }
=======
public class AppointmentImpl extends AppointmentGrpc.AppointmentImplBase {
>>>>>>> 7c5c0b0 (Setup the server)
}
