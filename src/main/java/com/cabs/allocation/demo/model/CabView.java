package com.cabs.allocation.demo.model;

import com.cabs.allocation.demo.types.CabType;

public class CabView {

    private final long cabId;
    private final double earningsTillNow;
    private final double distanceFromClient;
    private final Location location;
    private final CabType cabType;

    public CabView(CabViewBuilder cabViewBuilder) {
        this.cabId = cabViewBuilder.getCabId();
        this.earningsTillNow = cabViewBuilder.getEarningsTillNow();
        this.distanceFromClient = cabViewBuilder.getDistanceFromClient();
        this.location = cabViewBuilder.getLocation();
        this.cabType = cabViewBuilder.getCabType();
    }

    public long getCabId() {
        return cabId;
    }

    public double getEarningsTillNow() {
        return earningsTillNow;
    }

    public double getDistanceFromClient() {
        return distanceFromClient;
    }

    public Location getLocation() {
        return location;
    }

    public CabType getCabType() {
        return cabType;
    }

    @Override
    public String toString() {
        return "CabView{" +
                "cabId=" + cabId +
                ", earningsTillNow=" + earningsTillNow +
                ", distanceFromClient=" + distanceFromClient +
                ", location=" + location +
                ", cabType=" + cabType +
                '}';
    }
}
