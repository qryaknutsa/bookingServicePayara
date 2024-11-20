package com.example.bookingServicePayara.dao;


import com.example.bookingServicePayara.converter.CoordinatesConverter;
import com.example.bookingServicePayara.converter.PersonConverter;
import com.example.bookingServicePayara.converter.TicketConverter;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.converter.EventConverter;
import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.exception.TicketSavingException;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;


@Stateless
public class EventDao {
    private String SPRING_SERVICE_URL = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;


    //    @Transactional
    public void delete(int event_id) {
        Event event = em.find(Event.class, event_id);
        if (event != null) {
            em.remove(event);
        }
    }

    @Transactional
    public Object copyTicketWithDoublePriceAndVip(int ticketId, int personId) {
        Ticket foundTicket = findTicket(ticketId);
        TicketWrite newTicket = new TicketWrite();

        if (foundTicket.getPerson().getId() == personId) {
            if(foundTicket.getRefundable() != null) newTicket.setDiscount(foundTicket.getDiscount());
            newTicket.setCoordinates(CoordinatesConverter.toCoordinatesWrite(foundTicket.getCoordinates()));
            newTicket.setName(foundTicket.getName());
            newTicket.setPrice(foundTicket.getPrice() * 2);
            newTicket.setType(TicketType.VIP.name());
            newTicket.setPerson(PersonConverter.toPersonWrite(foundTicket.getPerson()));
            if(foundTicket.getRefundable() != null) newTicket.setRefundable(foundTicket.getRefundable());
            return saveTicket(newTicket);
        }
        return null;
    }

    private Event safeFind(Long id) {
        return em.find(Event.class, id);
    }


    @Transactional
    public Object save(EventWrite dto) {
        TicketWrite ticket = new TicketWrite();

        ticket.setCoordinates(dto.getCoordinates());
        ticket.setPrice(dto.getPrice());
        ticket.setName(dto.getTitle());
        ticket.setDiscount(dto.getDiscount());


        Object object = saveTickets(ticket, dto.getTicketsNum());
        List<Ticket> tickets = new ArrayList<>();
        String str = "";
        if(object instanceof String)str = (String) object;
        else tickets = (List<Ticket>) object;


//        List<Ticket> tickets = new ArrayList<>();
//        for (int i = 0; i < dto.getTicketsNum(); i++) {
//            tickets.add(ticket);
//        }

        if(!str.isEmpty()) return str;
        Event event = EventConverter.toEvent(dto);
        event.setTickets(tickets);
        em.persist(event);
        return event;
    }
    public Event getById(Long id) {
        return safeFind(id);
    }


    public Object saveTicket(TicketWrite ticket) {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) return response.readEntity(Ticket.class);
            else return response.readEntity(String.class);
        }
    }

    public Object saveTickets(TicketWrite ticket, int num) {
        List<Ticket> tickets = new ArrayList<>();

        try (Client client = ClientBuilder.newClient()) {
            for (int i = 0; i < num; i++) {
                Response response = client.target(SPRING_SERVICE_URL)
                        .request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

                if (response.getStatus() == 201) tickets.add(response.readEntity(Ticket.class));
                else return response.readEntity(String.class);
            }
        }
        return tickets;
    }


    private Ticket findTicket(int id) {
        String s = SPRING_SERVICE_URL + "/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)  // Меняем тип на XML
                    .get();
            return response.readEntity(Ticket.class);
        }
    }

//    private List<Ticket> getAllTickets() {
//        try (Client client = ClientBuilder.newClient()) {
//            return client.target(SPRING_SERVICE_URL)
//                    .request(MediaType.APPLICATION_JSON)  // Меняем тип на XML
//                    .get(new GenericType<List<Ticket>>() {
//                    });
//        }
//    }

}