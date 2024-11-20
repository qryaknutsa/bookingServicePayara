package com.example.bookingServicePayara.converter;


import com.example.bookingServicePayara.dto.LocationWrite;
import com.example.bookingServicePayara.model.Location;

public class LocationConverter {

    public static Location toLocation(LocationWrite locationWrite) {
        Location location = new Location();
        if (locationWrite.getName() != null) location.setName(locationWrite.getName());
        location.setX(locationWrite.getX());
        location.setY(locationWrite.getY());
        location.setZ(locationWrite.getZ());
        return location;
    }

    public static LocationWrite toLocationWrite(Location location) {
        LocationWrite locationWrite = new LocationWrite();
        if (location.getName() != null) locationWrite.setName(location.getName());
        locationWrite.setX(location.getX());
        locationWrite.setY(location.getY());
        locationWrite.setZ(location.getZ());
        return locationWrite;
    }
}
