package com.cabs.allocation.demo.util;

import com.cabs.allocation.demo.model.Location;

public class LocationPairToDistance {

    public static double distance(Location One, Location two) {
        return ConversionLocationDiffToMeters.convertToDistanceInMeters(One.getLatitude(),
                Math.abs(One.getLatitude() - two.getLatitude()),
                Math.abs(two.getLongitude() - One.getLongitude()));
    }
}
