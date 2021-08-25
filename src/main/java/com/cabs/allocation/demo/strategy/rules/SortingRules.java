package com.cabs.allocation.demo.strategy.rules;

import com.cabs.allocation.demo.model.CabView;

import java.util.*;

public class SortingRules extends AllotmentRules {
    Comparator<CabView> cabViewComparator = Comparator.comparingDouble(c -> getTotalWeightage(c));
    WeightageRulesAttributes[] weightageRulesAttributes;

    public SortingRules(WeightageRulesAttributes... rules) {
        this.weightageRulesAttributes = rules;
    }

    public double getTotalWeightage(CabView cabView) {
        if (Objects.isNull(weightageRulesAttributes)) throw new RuntimeException("Bad Rules - Rules list empty");
        return Arrays.stream(weightageRulesAttributes)
                .mapToDouble(w -> w.attributeFunction.apply(cabView) * w.weight)
                .sum();
    }
}