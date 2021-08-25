package com.cabs.allocation.demo.factory;

import com.cabs.allocation.demo.model.Cab;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.types.CabType;
import org.springframework.stereotype.Component;

@Component
public class CabFactory {

    public Cab create(long cabId, Location location, CabType cabType) {
        return new Cab(cabId, location, cabType);
    }
}
