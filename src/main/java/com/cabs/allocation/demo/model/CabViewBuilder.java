package com.cabs.allocation.demo.model;

import com.cabs.allocation.demo.types.CabType;
import com.cabs.allocation.demo.util.LocationPairToDistance;

public class CabViewBuilder {
    private long cabId;
    private double earningsTillNow;
    private double distanceFromClient;
    private Location location;
    private CabType cabType;

    public CabViewBuilder(Cab cab, Location clientLocation) {
        this.cabId = cab.getCabId();
        this.distanceFromClient = Math.round(LocationPairToDistance.distance(cab.getLocation(), clientLocation));
        this.earningsTillNow = cab.getCabEarnings().getEarningsForTheDay();
        this.location = cab.getLocation();
        this.cabType = cab.getCabType();
    }

    public CabViewBuilder() {

    }

    public CabView build() {
        return new CabView(this);
    }

    public long getCabId() {
        return cabId;
    }

    public CabViewBuilder setCabId(long cabId) {
        this.cabId = cabId;
        return this;
    }

    public double getDistanceFromClient() {
        return distanceFromClient;
    }

    public CabViewBuilder setDistanceFromClient(double distanceFromClient) {
        this.distanceFromClient = distanceFromClient;
        return this;
    }

    public double getEarningsTillNow() {
        return earningsTillNow;
    }

    public CabViewBuilder setEarningsTillNow(double earningsTillNow) {
        this.earningsTillNow = earningsTillNow;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public CabViewBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public CabType getCabType() {
        return cabType;
    }

    public void setCabType(CabType cabType) {
        this.cabType = cabType;
    }
}
