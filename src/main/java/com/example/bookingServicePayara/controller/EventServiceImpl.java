package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dao.TicketService;
import com.example.bookingServicePayara.dto.EventReadList;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.dto.TicketWithEventWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;

import java.security.InvalidParameterException;

@Stateless
@WebService(endpointInterface = "com.example.bookingServicePayara.controller.EventService")
public class EventServiceImpl implements EventService {
    @Inject
    private EventDao eventDao;


    @Override
    public String getQwe() {
        return "{\"message\": \"qwe\"}";
    }

    @Override
    public EventReadList getAllEvents() throws NotFoundException {
        return eventDao.getAll();
    }

    @Override
    public EventRead getEvent(String id) throws NotFoundException, InvalidParameterException {
        return eventDao.getById(id);
    }

    @Override
    public Event addEvent(@Valid EventWrite dto) throws TicketServiceNotAvailable, ValidationException {
        return eventDao.save(dto);
    }

    @Override
    public Ticket copyTicketWithDoublePriceAndVip(String ticket_id, String person_id) throws InvalidParameterException, NotFoundException, AlreadyVIPException,  TicketServiceNotAvailable {
        return eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
    }

    @Override
    public void deleteEvent(String event_id) throws InvalidParameterException, TooLateToDelete, NotFoundException {
        eventDao.delete(event_id);
    }

    @Override
    public Person getPerson(@WebParam(name = "id") String id) throws InvalidParameterException, NotFoundException {
        return TicketService.findPerson(Integer.parseInt(id));
    }

    @Override
    public TicketWithEventWrite getTicket(@WebParam(name = "id") String id) throws InvalidParameterException, NotFoundException {
        return TicketService.findTicket(Integer.parseInt(id));
    }

}