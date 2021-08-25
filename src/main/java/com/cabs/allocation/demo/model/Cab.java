package com.cabs.allocation.demo.model;

import com.cabs.allocation.demo.types.CabType;

public class Cab {

    private long cabId;
    private CabEarnings cabEarnings;
    private Location location;
    private CabType cabType;

    public Cab(long cabId, Location location) {
        this(cabId, location, CabType.NORMAL);
    }

    public Cab(long cabId, Location location, CabType cabType) {
        this.cabId = cabId;
        this.location = location;
        this.cabEarnings = new CabEarnings();
        this.cabType = cabType;
    }

    public long getCabId() {
        return cabId;
    }

    public Location getLocation() {
        return location;
    }

    public CabEarnings getCabEarnings() {
        return cabEarnings;
    }

    public CabType getCabType() {
        return cabType;
    }
}
