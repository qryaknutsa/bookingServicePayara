package com.example.bookingServicePayara.converter;

import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.model.Event;

public class EventConverter {

    public static Event toEvent(EventWrite dto){
        return new Event(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getEndTime(),
                CoordinatesConverter.toCoordinates(dto.getCoordinates()),
                dto.getPrice(),
                dto.getDiscount()
        );
    }


    public static EventWrite toEvent(Event dto){
        return new EventWrite(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getEndTime(),
                CoordinatesConverter.toCoordinatesWrite(dto.getCoordinates()),
                dto.getPrice(),
                dto.getDiscount()
        );
    }
}
