package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dto.EventWrite;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class EventController {

    @Inject
    private EventDao eventDao;


    @GET
    @Path("qwe")
    @Produces("application/json")
    public Response getQwe() {
        return Response.ok("{\"message\": \"qwe\"}").build(); // Возвращаем корректный JSON
    }

    @GET
    @Produces("application/json")
    public Response getAllEvents() {
        return Response.ok(eventDao.getAll()).build();
    }


    @GET
    @Path("event/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") String id) {
        return Response.ok(eventDao.getById(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvent(@Valid EventWrite dto) {
        Object event = eventDao.save(dto);
        return Response.status(201).entity(event).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sell/vip/{ticket_id}/{person_id}")
    public Response copyTicketWithDoublePriceAndVip(@PathParam("ticket_id") String ticket_id, @PathParam("person_id") String person_id) {
        Object e = eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
        return Response.ok(e).build();
    }

    @DELETE
    @Path("event/{event_id}/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(@PathParam("event_id") String event_id) {
        eventDao.delete(event_id);
        return Response.status(204).build();
    }

}