package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dto.EventReadList;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.validation.Valid;


@WebService(endpointInterface = "com.example.bookingServicePayara.controller.EventService")
public class EventServiceImpl implements EventService {
    @Inject
    private EventDao eventDao;


    @Override
    public String getQwe() {
        return "{\"message\": \"qwe\"}";
    }

    @Override
    public EventReadList getAllEvents() throws CustomNotFound{
        return eventDao.getAll();
    }

    @Override
    public EventRead getEvent(String id) throws CustomNotFound, InvalidParameter {
        return eventDao.getById(id);
    }

    @Override
    public Event addEvent(@Valid EventWrite dto) throws TicketServiceNotAvailable {
        return eventDao.save(dto);
    }

    @Override
    public Ticket copyTicketWithDoublePriceAndVip(String ticket_id, String person_id) throws IncorrectParameter, InvalidParameter, MultipleNotFound, AlreadyVIPException,  TicketServiceNotAvailable {
        return eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
    }

    @Override
    public void deleteEvent(String event_id) throws InvalidParameter, TooLateToDelete, CustomNotFound {
        eventDao.delete(event_id);
    }

}