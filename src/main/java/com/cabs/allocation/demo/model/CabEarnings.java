package com.cabs.allocation.demo.model;

public class CabEarnings {

    private double earningsForTheDay;

    public void addTripEarning(double tripMoney) {
        earningsForTheDay += tripMoney;
    }

    public double getEarningsForTheDay() {
        return earningsForTheDay;
    }
}
