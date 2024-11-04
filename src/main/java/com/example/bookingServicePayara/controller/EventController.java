package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dto.EventDto;
import com.example.bookingServicePayara.dto.EventMapper;
import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.exception.TicketSavingException;
import com.example.bookingServicePayara.model.Coordinates;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;

@Path("/events")
//@ApplicationScoped
public class EventController {

    @Inject
    private EventDao rm;

    private final String justOk = "Ok";

    // All Exceptions will be intercepted via ExceptionToStatus class
    private Response okWith(Object entity) {
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public Response getAllEvents() {
        return Response.ok("{\"message\": \"qwe\"}").build(); // Возвращаем корректный JSON
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") long id) {
        return okWith(rm.getById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addEvent(@Valid EventDto dto) {
        Event event = rm.save(dto);
        return Response.ok(event).build();
    }




}