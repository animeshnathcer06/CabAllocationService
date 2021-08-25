package com.cabs.allocation.demo.model;

import com.cabs.allocation.demo.types.CabType;

import java.util.List;

public class SearchOptions {
    private double searchRadiusInMeters;
    private Location location;
    private List<CabType> cabTypes;

    public double getSearchRadiusInMeters() {
        return searchRadiusInMeters;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSearchRadiusInMeters(double searchRadiusInMeters) {
        this.searchRadiusInMeters = searchRadiusInMeters;
    }

    public List<CabType> getCabTypes() {
        return cabTypes;
    }

    public void setCabTypes(List<CabType> cabTypes) {
        this.cabTypes = cabTypes;
    }
}
