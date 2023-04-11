package com.shayana;

import javax.jmdns.ServiceEvent;
import java.util.Calendar;

public class ServiceListener implements javax.jmdns.ServiceListener {
    @Override
    public void serviceAdded(ServiceEvent event) {
        Utils.statusList.add(Calendar.getInstance().getTime() +": Service Added "+event.getInfo().getName());

    }

    @Override
    public void serviceRemoved(ServiceEvent event) {
        Utils.statusList.add(Calendar.getInstance().getTime() +": Service Removed "+event.getInfo().getName());

    }

    @Override
    public void serviceResolved(ServiceEvent event) {
        Utils.statusList.add(Calendar.getInstance().getTime() +": Service Resolved "+event.getInfo().getName());

    }
}
