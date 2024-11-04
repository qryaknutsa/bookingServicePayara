package com.example.bookingServicePayara.dao;


import com.example.bookingServicePayara.dto.EventDto;
import com.example.bookingServicePayara.dto.EventMapper;
import com.example.bookingServicePayara.exception.TicketSavingException;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;


@Stateless
public class EventDao {
    private String SPRING_SERVICE_URL = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;


    private Event safeFind(Long id)  {
        return em.find(Event.class, id);
    }

    public Event save(EventDto dto) {
        Ticket ticket = new Ticket(
                dto.getTitle(),
                dto.getCoordinates(),
                dto.getPrice(),
                dto.getDiscount()
        );
        List<Ticket> tickets = saveTicket(ticket, dto.getTicketsNum());

//        for (Ticket savedTicket : tickets) {
//            em.merge(savedTicket);
//            em.merge(savedTicket.getCoordinates());
//        }

        Event event = EventMapper.toEvent(dto);
        event.setTickets(tickets);
        em.persist(event);
        return event;
    }

    public Event getById(Long id) {
        return safeFind(id);
    }


    public List<Ticket> saveTicket(Ticket ticket, int num) {
        List<Ticket> tickets = new ArrayList<>();

        try (Client client = ClientBuilder.newClient()) {
            for (int i = 0; i < num; i++) {
                Response response = client.target(SPRING_SERVICE_URL)
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

                if (response.getStatus() == 201) {
                    tickets.add(response.readEntity(Ticket.class));
                } else {
                    throw new TicketSavingException(tickets);
                }
            }
        }
        return tickets;
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