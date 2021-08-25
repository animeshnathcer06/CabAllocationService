package com.cabs.allocation.demo.strategy;


import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.service.lookup.CabsLookupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("defaultAllotmentStrategy")
public class NormalDistanceBasedStrategy implements CarAllotmentStrategy {

    @Value("${cabs.allocation.search.radiusInMeters}")
    Integer searchRadiusInMeters;

    @Resource
    CabsLookupService lookupService;

    @Override
    public List<CabView> findNearby(SearchOptions options) {
        fillOptionsIfEmpty(options);
        return lookupService.findNearByCabs(options);
    }

    private void fillOptionsIfEmpty(SearchOptions options) {
        if (options.getSearchRadiusInMeters() == 0)
            options.setSearchRadiusInMeters(searchRadiusInMeters);
    }
}