package com.shayana;

import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeStreamer implements StreamObserver<ListEmployeeRequest>{
   StreamObserver<ListEmployeeResponse> responseObserver;
    public EmployeeStreamer(StreamObserver<ListEmployeeResponse> responseObserver) {
        this.responseObserver=responseObserver;
    }

    @Override
    public void onNext(ListEmployeeRequest value) {
       responseObserver.onNext(getEmployees());
    }

    @Override
    public void onError(Throwable t) {
      responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
responseObserver.onCompleted();
    }

    public ListEmployeeResponse getEmployees(){
        ListEmployeeResponse.Builder builder=ListEmployeeResponse.newBuilder();
        try {
            Connection connection= org.shayana.Connection.connect();
            var sql="SELECT * FROM Employees";
            var result=connection.prepareStatement(sql).executeQuery();
            while (result.next()){
                builder.addEmployees(EmployeeItem.newBuilder()
                                .setEmployeeName(result.getString(2))
                                .setEmployeeId(result.getString(1))
                        .build());
            }
            connection.close();
            builder.setSuccess(true);
            builder.setMessage("Got "+builder.getEmployeesCount()+ " employees");
            return builder.build();
        }catch (SQLException e){
            builder.setSuccess(false);
            builder.setMessage(e.getMessage());
            return builder.build();
        }
    }
}
