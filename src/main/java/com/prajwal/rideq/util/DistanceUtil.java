package com.prajwal.rideq.util;

import com.prajwal.rideq.entity.Location;

public class DistanceUtil {
    private static final double EARTH_RADIUS_KM = 6371;

    public static double calculateDistance(Location l1, Location l2){
        if (l1 == null || l2 == null) {
            return Double.MAX_VALUE;
        }
        double lt1=Math.toRadians(l1.getLatitude());
        double lt2=Math.toRadians(l2.getLatitude());
        double ln1=Math.toRadians(l1.getLongitude());
        double ln2=Math.toRadians(l2.getLongitude());

        double lt=lt1-lt2;
        double ln=ln1-ln2;

        double a = Math.pow(Math.sin(lt / 2), 2)
                + Math.cos(lt1) * Math.cos(lt2)
                * Math.pow(Math.sin(ln / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS_KM * c;


    }


}
