package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.model.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("events")
public class EventController {

    @Inject
    private EventDao eventDao;


    @GET
    @Produces("application/json")
    public Response getAllEvents() {
        return Response.ok("{\"message\": \"qwe\"}").build(); // Возвращаем корректный JSON
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") long id) {
        return Response.ok(eventDao.getById(id)).build();
    }

    @GET
    @Path("discounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscounts() {
        return Response.ok(eventDao.getDiscounts()).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addEvent(@Valid EventWrite dto) {
        Object event = eventDao.save(dto);
        return Response.ok(event).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/sell/vip/{ticket_id}/{person_id}")
    public Response copyTicketWithDoublePriceAndVip(@PathParam("ticket_id") int ticket_id,@PathParam("person_id") int person_id) {
        Object e = eventDao.copyTicketWithDoublePriceAndVip(ticket_id, person_id);
        return Response.ok(e).build();
    }

    @GET
    @Path("/event/{event_id}/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEvent(@PathParam("event_id") int event_id) {
        Object q = eventDao.delete(event_id);
        return Response.ok(q).build();
    }

}