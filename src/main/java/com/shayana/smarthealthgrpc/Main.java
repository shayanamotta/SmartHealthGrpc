package com.shayana.smarthealthgrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

<<<<<<< HEAD
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
=======
import java.io.IOException;
import java.sql.SQLException;
>>>>>>> 7c5c0b0 (Setup the server)
import java.util.Calendar;

public class Main {
 static Server server;
<<<<<<< HEAD

    public static void main(String[] args) {

        org.shayana.Connection.connect();
=======
    public static void main(String[] args) throws SQLException {

        org.shayana.Connection.connect();
        org.shayana.Connection.init();
>>>>>>> 7c5c0b0 (Setup the server)

        startServer(args);
    }

    private static void startServer(String[] args) {
        try {
<<<<<<< HEAD
            server= ServerBuilder.forPort(8080)
=======
            server= ServerBuilder.forPort(8089)
>>>>>>> 7c5c0b0 (Setup the server)
                    .addService(new PriceListImpl())
                    .addService(new EmployeeImpl())
                    .addService(new AppointmentImpl())
                    .build()
                    .start();
<<<<<<< HEAD
            JmDNS dns=JmDNS.create(InetAddress.getLocalHost());
            ServiceListener listener=new ServiceListener();
            ServiceInfo serviceInfo=ServiceInfo.create("_http._tcp.local.",EmployeeGrpc.SERVICE_NAME,8080,"Employee Service");
            dns.registerService(serviceInfo);
            serviceInfo=ServiceInfo.create("_http._tcp.local.",AppointmentGrpc.SERVICE_NAME,8080,"Appointment Service");
            dns.registerService(serviceInfo);
            serviceInfo=ServiceInfo.create("_http._tcp.local.",PriceListGrpc.SERVICE_NAME,8080,"PriceList Service");
            dns.registerService(serviceInfo);
            dns.addServiceListener("_http._tcp.local.",listener);


=======
>>>>>>> 7c5c0b0 (Setup the server)
            Utils.statusList.add(Calendar.getInstance().getTime() +": Server started on port "+server.getPort());
            startClient(args);
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startClient(String[] args) {
        ClientStart.main(args);
    }


}