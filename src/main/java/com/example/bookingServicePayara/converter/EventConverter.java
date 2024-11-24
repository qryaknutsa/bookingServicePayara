package com.example.bookingServicePayara.converter;

import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.model.Event;

public class EventConverter {

    public static Event toEvent(EventWrite dto){
        Event event = new Event();
        event.setTitle(dto.getTitle());
        if(dto.getDescription() != null) event.setDescription(dto.getDescription());
        event.setPrice(dto.getPrice());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setCoordinates(CoordinatesConverter.toCoordinates(dto.getCoordinates()));
        event.setDiscount(dto.getDiscount());

        return event;
    }


    public static EventWrite toEventWrite(Event dto){
        EventWrite eventWrite = new EventWrite();
        eventWrite.setTitle(dto.getTitle());
        if(dto.getDescription() != null) eventWrite.setDescription(dto.getDescription());
        eventWrite.setPrice(dto.getPrice());
        eventWrite.setStartTime(dto.getStartTime());
        eventWrite.setEndTime(dto.getEndTime());
        eventWrite.setCoordinates(CoordinatesConverter.toCoordinatesWrite(dto.getCoordinates()));
        eventWrite.setDiscount(dto.getDiscount());
        eventWrite.setTicketsNum(dto.getTickets().size());
        return eventWrite;
    }

    public static EventRead toEventRead(Event dto){
        EventRead eventWrite = new EventRead();
        eventWrite.setId(dto.getId());
        eventWrite.setTitle(dto.getTitle());
        if(dto.getDescription() != null) eventWrite.setDescription(dto.getDescription());
        eventWrite.setPrice(dto.getPrice());
        eventWrite.setStartTime(dto.getStartTime());
        eventWrite.setEndTime(dto.getEndTime());
        eventWrite.setCoordinates(CoordinatesConverter.toCoordinatesWrite(dto.getCoordinates()));
        eventWrite.setDiscount(dto.getDiscount());
        eventWrite.setTicketsNum(dto.getTickets().size());
        return eventWrite;
    }
}
