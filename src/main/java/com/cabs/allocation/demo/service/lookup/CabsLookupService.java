package com.cabs.allocation.demo.service.lookup;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.repository.CabDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CabsLookupService {

    @Autowired
    CabDataStore cabDataStore;

    public List<CabView> findNearByCabs(SearchOptions options) {
        return cabDataStore.findNearestCabs(options);
    }

}
