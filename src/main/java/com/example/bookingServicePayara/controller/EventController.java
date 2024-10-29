package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.model.Coordinates;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    private String SPRING_SERVICE_URL = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";

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
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addEvent() {
        Ticket ticket = new Ticket();
        ticket.setName("try6");
        Coordinates coordinates = new Coordinates(10, 10f);
        ticket.setCoordinates(coordinates);
        ticket.setPrice(123123);
        ticket.setDiscount(12.1);
//        ticket.setType(TicketType.BUDGETARY);

        Ticket savedTicket = saveTicket(ticket);

        rm.getEm().merge(savedTicket);

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(savedTicket); // Добавляем объект с ID

        Event event = new Event();
        event.setTitle("try7");
        event.setDescription("description");
        event.setTickets(tickets);
        rm.save(event);
        return Response.ok(event).build();
    }


    public Ticket saveTicket(Ticket ticket) {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) {
                return response.readEntity(Ticket.class); // Возвращаем обновленный билет с ID
            } else {
                throw new RuntimeException("Failed to save ticket on remote service.");
            }
        }
    }

    private Ticket findTicket(int id) {
        String s = SPRING_SERVICE_URL + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)  // Меняем тип на XML
                    .get();
            return response.readEntity(Ticket.class);
        }
    }

    private List<Ticket> getAllTickets() {
        try (Client client = ClientBuilder.newClient()) {
            return client.target(SPRING_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)  // Меняем тип на XML
                    .get(new GenericType<List<Ticket>>() {
                    });
        }
    }

}