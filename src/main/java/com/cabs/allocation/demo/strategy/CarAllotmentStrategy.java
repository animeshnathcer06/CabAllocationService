package com.cabs.allocation.demo.strategy;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;

import java.util.List;

public interface CarAllotmentStrategy {

    List<CabView> findNearby(SearchOptions options);
}
