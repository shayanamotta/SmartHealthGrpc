package com.shayana;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Booking implements Serializable {
    private String clientId;
    private String serviceBId;

    public Booking(String serviceId, String clientId) {

    }
    public Booking(){}

    public String getClientId() {
        return clientId;
    }

    public String getServiceBId() {
        return serviceBId;
    }

    public void setServiceBId(String serviceBId) {
        this.serviceBId = serviceBId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        Map<String,String> booking=new HashMap<>();
        booking.put("serviceBId",getServiceBId());
        booking.put("clientId",getClientId());
        return booking.toString();
    }
}
