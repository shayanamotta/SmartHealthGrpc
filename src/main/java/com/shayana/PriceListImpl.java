package com.shayana;

import io.grpc.stub.StreamObserver;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class PriceListImpl extends PriceListGrpc.PriceListImplBase {
    @Override
    public void addToPriceList(PriceListAddRequest request, StreamObserver<PriceListResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" add service requested");
        Connection connection= org.shayana.Connection.connect();
        var query="INSERT INTO Services(ServiceID,name,timeoffered,cost,slots)VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(query);

            for (Service service : request.getServicesList()) {
                statement.setString(1, service.getServiceId());
                statement.setString(2, service.getServiceName());
                statement.setString(3, service.getTimeoffered());
                statement.setInt(4, service.getCost());
                statement.setInt(5, service.getSlots());
             }

            statement.closeOnCompletion();
            connection.close();
            responseObserver.onNext(PriceListResponse.newBuilder()
                    .setMessage("Service Added")
                    .addAllServices(request.getServicesList())
                    .setSuccess(true)
                    .build());
            responseObserver.onCompleted();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    @Override
    public void listPriceList(PriceListRequest request, StreamObserver<PriceListResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" service list requested");

        try {
            Connection connection= org.shayana.Connection.connect();
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM Services");
            ResultSet resultSet= statement.executeQuery();

            PriceListResponse.Builder builder=PriceListResponse.newBuilder();
            builder.setMessage("Fetched services");
            builder.setSuccess(true);
            while (resultSet.next()){
                var id=resultSet.getString(1);
                var name=resultSet.getString(2);
                var time=resultSet.getString(3);
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
            JOptionPane.showMessageDialog(null,"SQL EXCEPTION "+e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void removeFromPriceList(PriceListRemoveRequest request, StreamObserver<PriceListResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" remove service requested");

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
                var time=resultSet.getString(3);
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
            builder.setMessage("Service Removed");
            builder.setSuccess(true);
           return builder.build();
        }catch (SQLException e){
            builder.setSuccess(false);
            builder.setMessage(e.getMessage());
            return builder.build();
        }
    }
}
