package com.cabs.allocation.demo.strategy.rules;

import com.cabs.allocation.demo.model.CabView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesEngine {

    public List<CabView> filterAndOrder(List<CabView> nearbyCarsInRange, SortingRules sortingRules, FilteringRules filteringRules) {
        nearbyCarsInRange.sort(sortingRules.cabViewComparator);
        return nearbyCarsInRange;
    }
}
