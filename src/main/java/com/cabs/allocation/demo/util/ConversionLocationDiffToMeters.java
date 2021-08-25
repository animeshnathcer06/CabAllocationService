package com.cabs.allocation.demo.util;

/**
 * https://gis.stackexchange.com/questions/142326/calculating-longitude-length-in-miles
 *
  */
public class ConversionLocationDiffToMeters {

    public static final double LATITUDE_TO_METERS_RATE = 111_320;
    public static final double LONGITUDE_DIFF_AT_EQUATOR = 111_699;

    public static double convertToDistanceInMeters(double averageLatitude,
                                            double latitudeDifference,
                                            double longitudeDifference) {

        double latitudeDiffInMeters = getLatitudeDiffInMeters(latitudeDifference);
        double longitudeDiffInMeters = getLongitudeInMeters(averageLatitude, longitudeDifference);
        return longitudeDiffInMeters + latitudeDiffInMeters;
    }

    private static double getLatitudeDiffInMeters(double latitudeDifference) {
        return latitudeDifference * LATITUDE_TO_METERS_RATE;
    }

    private static double getLongitudeInMeters(double averageLatitude, double longitudeDifference) {
        double latitideInRadians = Math.toRadians(averageLatitude);
        return Math.cos(latitideInRadians) * longitudeDifference * LONGITUDE_DIFF_AT_EQUATOR;
    }
}
