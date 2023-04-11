package com.shayana;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import javax.swing.*;
import java.util.Calendar;
import java.util.Iterator;

public class Client{
   PriceListGrpc.PriceListBlockingStub priceListBlockingStub;
   PriceListGrpc.PriceListStub priceListStub;
   EmployeeGrpc.EmployeeBlockingStub employeeBlockingStub;
   AppointmentGrpc.AppointmentBlockingStub appointmentBlockingStub;

   EmployeeGrpc.EmployeeStub employeeStub;

   public Client(){
       try {
           ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",8080)
                   .enableRetry()
                   .usePlaintext()
                   .build();
           priceListBlockingStub=PriceListGrpc.newBlockingStub(channel);
           employeeBlockingStub=EmployeeGrpc.newBlockingStub(channel);
           appointmentBlockingStub=AppointmentGrpc.newBlockingStub(channel);
           priceListStub=PriceListGrpc.newStub(channel);
           employeeStub=EmployeeGrpc.newStub(channel);

       }catch (Exception e){
           e.printStackTrace();
           Utils.statusList.add(Calendar.getInstance().getTime()+" Client error "+e.getMessage());

       }

   }
   public void addService(Service service){
       Iterator<PriceListResponse> priceListResponses=priceListBlockingStub.addToPriceList(PriceListAddRequest.newBuilder()
               .addServices(service)
               .build());
       var index=0;
       while (priceListResponses.hasNext()){
           PriceListResponse response=priceListResponses.next();
           Utils.priceListEvents.add(response.getMessage());
           if (!response.getSuccess()){
               JOptionPane.showMessageDialog(null,response.getMessage());
               index++;
               continue;
           }
           Service service1=response.getServices(index);
           Utils.serviceList.add(service1);
           Utils.priceListEvents.add(response.getMessage());
index++;
       }
        }

    public void getServices() {
priceListStub.listPriceList(PriceListRequest.newBuilder()
        .setShowall(true)
        .build(), new StreamObserver<>() {
    @Override
    public void onNext(PriceListResponse value) {
        Utils.priceListEvents.add(value.getMessage());
        if (!value.getSuccess()){
            JOptionPane.showMessageDialog(null,"Failed "+value.getMessage());
           return;
        }
        for (Service service:
            value.getServicesList() ) {
            Utils.serviceList.add(service);
        }

    }

    @Override
    public void onError(Throwable t) {
        JOptionPane.showMessageDialog(null,"Grpc Error"+t.getMessage());

    }

    @Override
    public void onCompleted() {

    }
});
    }

    public void removeService(String serviceId){
       priceListStub.removeFromPriceList(PriceListRemoveRequest.newBuilder()
               .setServiceId(serviceId).build(), new StreamObserver<>() {
           @Override
           public void onNext(PriceListResponse value) {
               Utils.priceListEvents.add(value.getMessage());
               if (!value.getSuccess()){
                   JOptionPane.showMessageDialog(null,"Failed "+value.getMessage());
                   return;
               }
               Utils.serviceList.clear();
               for (Service service:
                       value.getServicesList() ) {
                   Utils.serviceList.add(service);
               }
           }

           @Override
           public void onError(Throwable t) {
               JOptionPane.showMessageDialog(null,"Grpc Error"+t.getMessage());

           }

           @Override
           public void onCompleted() {

           }
       });
    }

    public void bookAppointMent(AppointmentRequest build) {
        SmartResponse response=appointmentBlockingStub.makeAppointment(build);
        Utils.appointmentEvents.add(response.getMessage());
    }

    public void checkServiceAvailable(CheckAvailabilityRequest build) {
        SmartResponse response= appointmentBlockingStub.checkAvailability(build).next();
        Utils.appointmentEvents.add(response.getMessage());
    }

    public void cancelBooking(CancellationRequest.Builder setServiceId) {
        SmartResponse response=appointmentBlockingStub.cancelAppointment(setServiceId.build());
        Utils.appointmentEvents.add(response.getMessage());
    }

    public void getEmployees(){
       var request=employeeStub.listEmployee(new StreamObserver<>() {
           @Override
           public void onNext(ListEmployeeResponse value){
               Utils.employeeItems.clear();
               Utils.employeeItems.addAll(value.getEmployeesList());
               Utils.employeeEvents.add(value.getMessage());
           }

           @Override
           public void onError(Throwable t) {
Utils.employeeEvents.add(t.getMessage());
           }

           @Override
           public void onCompleted() {

           }
       });
       request.onNext(ListEmployeeRequest.newBuilder().build());
    }

    public void addEmployee(AddEmployeeRequest request){
       var employeeItem=employeeBlockingStub.addEmployee(request).next();
       System.out.println(employeeItem.getEmployeesList());
       Utils.employeeEvents.add(employeeItem.getMessage());
       Utils.employeeItems.addAll(employeeItem.getEmployeesList());
    }

    public void deleteEmployees(DeleteEmployeeRequest request){
       var employees=employeeBlockingStub.deleteEmployee(request);
       Utils.employeeItems.clear();
       Utils.employeeItems.addAll(employees.next().getEmployeesList());
       Utils.employeeEvents.add("Employee deleted");
    }
}
