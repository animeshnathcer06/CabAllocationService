package com.cabs.allocation.demo.repository;

import com.cabs.allocation.demo.model.Cab;
import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.types.CabType;

import java.util.List;

public interface CabDataStore {

    Cab addCabToDataStore(Location location, CabType cabType);

    void removeCabFromDataStore(Cab cab);

    List<CabView> findNearestCabs(SearchOptions options);

    Cab addTrip(long cabId, double amount);
}
