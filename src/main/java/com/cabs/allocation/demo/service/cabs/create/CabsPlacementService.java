package com.cabs.allocation.demo.service.cabs.create;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.CabViewBuilder;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.repository.CabDataStore;
import com.cabs.allocation.demo.types.CabType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CabsPlacementService {

    @Resource
    CabDataStore cabStore;

    public CabView placeNewCabOnMap(Location cabLocation, CabType cabType) {
        return new CabViewBuilder(cabStore.addCabToDataStore(cabLocation, cabType), cabLocation).build();
    }
}
