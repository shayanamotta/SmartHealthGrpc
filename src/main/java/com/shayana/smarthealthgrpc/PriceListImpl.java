package com.shayana.smarthealthgrpc;

import io.grpc.stub.StreamObserver;

<<<<<<< HEAD
import javax.swing.*;
=======
>>>>>>> 7c5c0b0 (Setup the server)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.Calendar;
=======
>>>>>>> 7c5c0b0 (Setup the server)

public class PriceListImpl extends PriceListGrpc.PriceListImplBase {
    @Override
    public void addToPriceList(PriceListAddRequest request, StreamObserver<PriceListResponse> responseObserver) {
<<<<<<< HEAD
        Utils.statusList.add(Calendar.getInstance().getTime()+" add service requested");
=======
>>>>>>> 7c5c0b0 (Setup the server)
        Connection connection= org.shayana.Connection.connect();
        var query="INSERT INTO Services(ServiceID,name,timeoffered,cost,slots)VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(query);

            for (Service service : request.getServicesList()) {
                statement.setString(1, service.getServiceId());
                statement.setString(2, service.getServiceName());
<<<<<<< HEAD
                statement.setString(3, service.getTimeoffered());
                statement.setInt(4, service.getCost());
                statement.setInt(5, service.getSlots());
             }

            statement.closeOnCompletion();
            connection.close();
            responseObserver.onNext(PriceListResponse.newBuilder()
                    .setMessage("Service Added")
=======
                statement.setLong(3, service.getTimeoffered());
                statement.setInt(4, service.getCost());
                statement.setInt(5, service.getSlots());
                statement.execute();
            }
            statement.closeOnCompletion();
            connection.close();
            responseObserver.onNext(PriceListResponse.newBuilder()
                    .setMessage("User Added")
>>>>>>> 7c5c0b0 (Setup the server)
                    .addAllServices(request.getServicesList())
                    .setSuccess(true)
                    .build());
            responseObserver.onCompleted();
        }catch (SQLException e){
<<<<<<< HEAD
            JOptionPane.showMessageDialog(null,e.getMessage());
=======
>>>>>>> 7c5c0b0 (Setup the server)
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    @Override
    public void listPriceList(PriceListRequest request, StreamObserver<PriceListResponse> responseObserver) {
<<<<<<< HEAD
        Utils.statusList.add(Calendar.getInstance().getTime()+" service list requested");

=======
>>>>>>> 7c5c0b0 (Setup the server)
        try {
            Connection connection= org.shayana.Connection.connect();
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM Services");
            ResultSet resultSet= statement.executeQuery();

            PriceListResponse.Builder builder=PriceListResponse.newBuilder();
<<<<<<< HEAD
            builder.setMessage("Fetched services");
            builder.setSuccess(true);
            while (resultSet.next()){
                var id=resultSet.getString(1);
                var name=resultSet.getString(2);
                var time=resultSet.getString(3);
=======
            while (resultSet.next()){
                var id=resultSet.getString(1);
                var name=resultSet.getString(2);
                var time=resultSet.getLong(3);
>>>>>>> 7c5c0b0 (Setup the server)
                var cost=resultSet.getInt(4);
                var slots=resultSet.getInt(5);
                builder.addServices(Service.newBuilder()
                                .setCost(cost)
                                .setServiceId(id)
                                .setServiceName(name)
                                .setTimeoffered(time)
                                .setSlots(slots)
                        .build());

            }
            statement.closeOnCompletion();
            connection.close();
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }catch (SQLException e){
<<<<<<< HEAD
            JOptionPane.showMessageDialog(null,"SQL EXCEPTION "+e.getMessage());
=======
>>>>>>> 7c5c0b0 (Setup the server)
            responseObserver.onError(e);
        }
    }

    @Override
    public void removeFromPriceList(PriceListRemoveRequest request, StreamObserver<PriceListResponse> responseObserver) {
<<<<<<< HEAD
        Utils.statusList.add(Calendar.getInstance().getTime()+" remove service requested");

=======
>>>>>>> 7c5c0b0 (Setup the server)
        try {
            Connection connection= org.shayana.Connection.connect();
            var query="DELETE FROM Services WHERE serviceId=?";
            var statement=connection.prepareStatement(query);
            statement.setString(1,request.getServiceId());
            statement.execute();
            statement.closeOnCompletion();

            PriceListResponse response=getPriceList();
            if (!response.getSuccess()){
                responseObserver.onError(new Throwable(response.getMessage()));
            }else {
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

        }catch (SQLException e){
            responseObserver.onError(e);
        }
    }


    private PriceListResponse getPriceList(){
        PriceListResponse.Builder builder=PriceListResponse.newBuilder();

        try {
            Connection connection= org.shayana.Connection.connect();
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM Services");
            ResultSet resultSet= statement.executeQuery();

            while (resultSet.next()){
                var id=resultSet.getString(1);
                var name=resultSet.getString(2);
<<<<<<< HEAD
                var time=resultSet.getString(3);
=======
                var time=resultSet.getLong(3);
>>>>>>> 7c5c0b0 (Setup the server)
                var cost=resultSet.getInt(4);
                var slots=resultSet.getInt(5);
                builder.addServices(Service.newBuilder()
                        .setCost(cost)
                        .setServiceId(id)
                        .setServiceName(name)
                        .setTimeoffered(time)
                        .setSlots(slots)
                        .build());

            }
            statement.closeOnCompletion();
            connection.close();
<<<<<<< HEAD
            builder.setMessage("Service Removed");
=======
            builder.setMessage("Got Users");
>>>>>>> 7c5c0b0 (Setup the server)
            builder.setSuccess(true);
           return builder.build();
        }catch (SQLException e){
            builder.setSuccess(false);
            builder.setMessage(e.getMessage());
            return builder.build();
        }
    }
}
