package com.example.bookingServicePayara.converter;


import com.example.bookingServicePayara.dto.CoordinatesWrite;
import com.example.bookingServicePayara.model.Coordinates;

public class CoordinatesConverter {
    public static Coordinates toCoordinates(CoordinatesWrite coordinatesWrite) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesWrite.getX());
        coordinates.setY(coordinatesWrite.getY());
        return coordinates;
    }

    public static CoordinatesWrite toCoordinatesWrite(Coordinates coordinatesWrite) {
        CoordinatesWrite coordinates = new CoordinatesWrite();
        coordinates.setX(coordinatesWrite.getX());
        coordinates.setY(coordinatesWrite.getY());
        return coordinates;
    }


}
