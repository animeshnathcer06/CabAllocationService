package com.cabs.allocation.demo.service;

import com.cabs.allocation.demo.model.Cab;
import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.CabViewBuilder;
import com.cabs.allocation.demo.repository.CabDataStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TripService {

    @Resource
    CabDataStore cabDataStore;

    public CabView addTrip(long cabId, double amount) {
        Cab cab = cabDataStore.addTrip(cabId, amount);
        return new CabViewBuilder(cab, cab.getLocation()).build();
    }
}
