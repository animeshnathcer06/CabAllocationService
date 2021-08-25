package com.cabs.allocation.demo.strategy;


import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.service.lookup.CabsLookupService;
import com.cabs.allocation.demo.strategy.rules.FilteringRules;
import com.cabs.allocation.demo.strategy.rules.RulesEngine;
import com.cabs.allocation.demo.strategy.rules.SortingRules;
import com.cabs.allocation.demo.strategy.rules.WeightageRulesAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service("ruleAllotmentStrategy")
public class RuleBasedBasedAllotmentStrategy implements CarAllotmentStrategy {

    SortingRules sortingRules;
    FilteringRules filteringRules;

    @Value("${cabs.allocation.search.radiusInMeters}")
    Integer searchRadiusInMeters;

    @Resource
    CabsLookupService lookupService;

    @Resource
    RulesEngine rulesEngine;

    @PostConstruct
    void init() {
        var weightByEarnings = new WeightageRulesAttributes(CabView::getEarningsTillNow, 10);
        var weightByDistance = new WeightageRulesAttributes(CabView::getDistanceFromClient, 5);
        sortingRules = new SortingRules(weightByEarnings, weightByDistance);
        filteringRules = new FilteringRules();
    }

    @Override
    public List<CabView> findNearby(SearchOptions options) {
        fillOptionsIfEmpty(options);
        List<CabView> nearbyCarsInRange = lookupService.findNearByCabs(options);
        return rulesEngine.filterAndOrder(nearbyCarsInRange, sortingRules, filteringRules);
    }

    private void fillOptionsIfEmpty(SearchOptions options) {
        if (options.getSearchRadiusInMeters() == 0)
            options.setSearchRadiusInMeters(searchRadiusInMeters);
    }
}