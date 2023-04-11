package com.shayana;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;

public class Main {
 static Server server;

    public static void main(String[] args) {

        org.shayana.Connection.connect();

        startServer(args);
    }

    private static void startServer(String[] args) {
        try {
            server= ServerBuilder.forPort(8080)
                    .addService(new PriceListImpl())
                    .addService(new EmployeeImpl())
                    .addService(new AppointmentImpl())
                    .build()
                    .start();
            JmDNS dns=JmDNS.create(InetAddress.getLocalHost());
            ServiceListener listener=new ServiceListener();
            ServiceInfo serviceInfo=ServiceInfo.create("_http._tcp.local.",EmployeeGrpc.SERVICE_NAME,8080,"Employee Service");
            dns.registerService(serviceInfo);
            serviceInfo=ServiceInfo.create("_http._tcp.local.",AppointmentGrpc.SERVICE_NAME,8080,"Appointment Service");
            dns.registerService(serviceInfo);
            serviceInfo=ServiceInfo.create("_http._tcp.local.",PriceListGrpc.SERVICE_NAME,8080,"PriceList Service");
            dns.registerService(serviceInfo);
            dns.addServiceListener("_http._tcp.local.",listener);


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