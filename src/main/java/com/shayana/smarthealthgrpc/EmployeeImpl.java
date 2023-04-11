package com.shayana.smarthealthgrpc;

<<<<<<< HEAD
import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

public class EmployeeImpl extends EmployeeGrpc.EmployeeImplBase {
    @Override
    public void deleteEmployee(DeleteEmployeeRequest request, StreamObserver<ListEmployeeResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" delete employee requested");

        try {
            Connection connection = org.shayana.Connection.connect();
            var sql = "DELETE FROM Employees WHERE EmployeeID=?";
            var statement = connection.prepareStatement(sql);
            statement.setString(1, request.getEmployeeId());
            statement.execute();
            statement.close();
            connection.close();
            responseObserver.onNext(ClientStart.streamer.getEmployees());
            responseObserver.onCompleted();

        }catch (SQLException e){
            e.printStackTrace();
            responseObserver.onError(e);
        }

    }

    @Override
    public void addEmployee(AddEmployeeRequest request, StreamObserver<ListEmployeeResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" add employee requested");

        try {
            Connection connection= org.shayana.Connection.connect();
            var sql="INSERT INTO Employees (EmployeeID,EmployeeName) VALUES(?,?)";
            var statement=connection.prepareStatement(sql);
            statement.setString(1,request.getEmployee().getEmployeeId());
            statement.setString(2,request.getEmployee().getEmployeeName());
            statement.execute();
            statement.closeOnCompletion();
            connection.close();
            responseObserver.onNext(ListEmployeeResponse.newBuilder()
                            .addEmployees(request.getEmployee())
                            .setSuccess(true)
                            .setMessage("Employee added")
                    .build());
        }catch (SQLException e){
            e.printStackTrace();
            responseObserver.onError(e);
        }
    }

    @Override
    public StreamObserver<ListEmployeeRequest> listEmployee(StreamObserver<ListEmployeeResponse> responseObserver) {
        Utils.statusList.add(Calendar.getInstance().getTime()+" Employee streaming requested");

        ClientStart.registerStreamer(responseObserver);
        return ClientStart.streamer;
    }
=======
public class EmployeeImpl extends EmployeeGrpc.EmployeeImplBase {
>>>>>>> 7c5c0b0 (Setup the server)
}
