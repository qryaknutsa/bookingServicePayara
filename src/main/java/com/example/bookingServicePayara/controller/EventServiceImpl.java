package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.model.Event;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import jakarta.xml.soap.SOAPException;

import java.util.List;


@WebService(endpointInterface = "com.example.bookingServicePayara.controller.EventService")
public class EventServiceImpl implements EventService {
    @Inject
    private EventDao eventDao;


    @Override
    public String getQwe() {
        return "{\"message\": \"qwe\"}";
    }

    @Override
    public Object getAllEvents() throws SOAPException{
        return eventDao.getAll();
    }

    @Override
    public EventRead getEvent(String id) throws SOAPException {
        return eventDao.getById(id);
    }

    @Override
    public Object addEvent(@Valid EventWrite dto) throws SOAPException{
        return eventDao.save(dto);
    }

    @Override
    public Object copyTicketWithDoublePriceAndVip(String ticket_id, String person_id) throws SOAPException{
        return eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
    }

    @Override
    public Object deleteEvent(String event_id) throws SOAPException{
        return eventDao.delete(event_id);
    }

}