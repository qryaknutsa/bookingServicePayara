package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.model.Event;

public class EventMapper {

    public static Event toEvent(EventDto dto){
        return new Event(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getCoordinates(),
                dto.getPrice(),
                dto.getDiscount()
        );
    }


    public static EventDto toEvent(Event dto){
        return new EventDto(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getCoordinates(),
                dto.getPrice(),
                dto.getDiscount()
        );
    }
}
