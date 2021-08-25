package com.cabs.allocation.demo.service;

import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.strategy.CarAllotmentStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CabAllotmentService {

    @Resource(name = "ruleAllotmentStrategy")
    CarAllotmentStrategy carAllotmentStrategy;

    public List<CabView> findNearByCabs(SearchOptions options) {
        return carAllotmentStrategy.findNearby(options);
    }
}
