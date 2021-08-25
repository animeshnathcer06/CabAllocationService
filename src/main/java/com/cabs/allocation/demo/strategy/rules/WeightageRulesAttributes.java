package com.cabs.allocation.demo.strategy.rules;

import com.cabs.allocation.demo.model.CabView;

import java.util.function.Function;

public class WeightageRulesAttributes {
    double weight;
    Function<CabView, Double> attributeFunction;

    public WeightageRulesAttributes(Function<CabView, Double> attributeFunction, double weight) {
        this.attributeFunction = attributeFunction;
        this.weight = weight;
    }
}
