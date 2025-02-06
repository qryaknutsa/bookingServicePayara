package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dao.TicketService;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.converter.EventConverter;
import com.example.bookingServicePayara.dto.TicketWithEventWrite;
import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@WebService(endpointInterface = "com.example.bookingServicePayara.controller.EventService")
public class EventServiceImpl implements EventService {
    @Inject
    private EventDao eventDao;


    @Override
    public Response getQwe() {
        return Response.ok("{\"message\": \"qwe\"}").build(); // Возвращаем корректный JSON
    }

    @Override
    public Response getAllEvents() {
        return Response.ok(eventDao.getAll()).build();
    }


    @Override
    public Response getEvent(String id) {
        return Response.ok(eventDao.getById(id)).build();
    }

    @Override
    public Response addEvent(@Valid EventWrite dto) {
        Object event = eventDao.save(dto);
        return Response.status(201).entity(event).build();
    }

    @Override
    public Response copyTicketWithDoublePriceAndVip(String ticket_id, String person_id) {
        Object e = eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
        return Response.ok(e).build();
    }

    @Override
    public Response deleteEvent(String event_id) {
        eventDao.delete(event_id);
        return Response.status(204).build();
    }

}