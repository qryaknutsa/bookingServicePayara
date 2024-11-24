package com.example.bookingServicePayara.dao;


import com.example.bookingServicePayara.converter.CoordinatesConverter;
import com.example.bookingServicePayara.converter.PersonConverter;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.converter.EventConverter;
import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class EventDao {
//    private String SPRING_SERVICE_URL = "http://localhost:8090/ticketservicepayara/TMA/api/v2/tickets";
private final String SPRING_SERVICE_URL = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;


    public List<EventRead> getAll() {
        List<Event> list = em.createQuery("SELECT l FROM Event l", Event.class).getResultList();
        List<EventRead> toReturn = new ArrayList<>();

        for (Event e : list) {
            EventRead eventRead = EventConverter.toEventRead(e);
            toReturn.add(eventRead);
        }

        if (toReturn.isEmpty()) throw new CustomNotFound("По вашему запросу мероприятия не найдены.");
        return toReturn;
    }

    public EventRead getById(String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            List<String> messages = new ArrayList<>();
            messages.add("Некорректное значение параметра ticket_id: " + idStr);
            throw new IncorrectParameter(messages);
        }
        Event e = em.find(Event.class, id);
        if (id < 0) {
            List<String> messages = new ArrayList<>();
            messages.add("Значение event_id должно быть больше нуля");
            throw new InvalidParameter(messages);
        }
        if (e == null) throw new CustomNotFound("По вашему запросу мероприятие не найдено.");
        return EventConverter.toEventRead(e);
    }


    public void delete(String eventIdStr) {
        int event_id;
        try {
            event_id = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            List<String> messages = new ArrayList<>();
            messages.add("Некорректное значение параметра ticket_id: " + eventIdStr);
            throw new IncorrectParameter(messages);
        }
        if (event_id < 0) {
            List<String> messages = new ArrayList<>();
            messages.add("Значение event_id должно быть больше нуля");
            throw new InvalidParameter(messages);
        }

        Event event = em.find(Event.class, event_id);
        if (event != null) {
            if (event.getStartTime().isBefore(ZonedDateTime.now()))
                throw new TooLateToDelete("Мероприятие уже началось, отменить невозможно.");
            else if (ZonedDateTime.now().isAfter(event.getEndTime()))
                throw new TooLateToDelete("Мероприятие уже прошло, отменить невозможно.");
            else em.remove(event);
        } else {
            throw new CustomNotFound("По вашему запросу мероприятие не найдено.");
        }
    }


    public Object getDiscounts() {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL + "/discounts")
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200)
                return response.readEntity(Double.class);
            else return response.readEntity(String.class);
        }
    }

    public Object copyTicketWithDoublePriceAndVip(String ticketIdStr, String personIdStr) {
        int ticketId = 0;
        int personId = 0;
        boolean invalidTicketId = false;
        boolean invalidPersonId = false;
        List<String> messages = new ArrayList<>();

        try {
            ticketId = Integer.parseInt(ticketIdStr);
        } catch (NumberFormatException e) {
            invalidTicketId = true;
        }
        try {
            personId = Integer.parseInt(personIdStr);
        } catch (NumberFormatException e) {
            invalidPersonId = true;
        }
        if (invalidTicketId && invalidPersonId) {
            messages.add("Некорректное значение параметра ticket_id: " + ticketIdStr);
            messages.add("Некорректное значение параметра person_id: " + personIdStr);
            throw new IncorrectParameter(messages);
        }
        if (invalidTicketId) {
            messages.add("Некорректное значение параметра ticket_id: " + ticketIdStr);
            throw new IncorrectParameter(messages);
        }
        if (invalidPersonId) {
            messages.add("Некорректное значение параметра person_id: " + personIdStr);
            throw new IncorrectParameter(messages);
        }
        if (ticketId < 0) invalidTicketId = true;
        if (personId < 0) invalidPersonId = true;
        if (invalidTicketId && invalidPersonId) {
            messages.add("Значение ticket_id должно быть больше нуля");
            messages.add("Значение person_id должно быть больше нуля");
            throw new InvalidParameter(messages);
        }
        if (invalidTicketId) {
            messages.add("Значение ticket_id должно быть больше нуля");
            throw new InvalidParameter(messages);
        }
        if (invalidPersonId) {
            messages.add("Значение person_id должно быть больше нуля");
            throw new InvalidParameter(messages);
        }

        Ticket foundTicket = findTicket(ticketId);
        Person foundPerson = findPerson(personId);

        if (foundTicket == null) invalidTicketId = true;
        if (foundPerson == null) invalidPersonId = true;
        if (invalidTicketId && invalidPersonId) {
            messages.add("Билет с данным ID не найден.");
            messages.add("Человек с данным ID не найден.");
            throw new MultipleNotFound(messages);
        }
        if (invalidTicketId) {
            messages.add("Билет с данным ID не найден.");
            throw new MultipleNotFound(messages);
        }
        if (invalidPersonId) {
            messages.add("Человек с данным ID не найден.");
            throw new MultipleNotFound(messages);
        }

        if (foundTicket.getPerson() == null) {
            messages.add("У билета нет владельца.");
            throw new IncorrectParameter(messages);
        }

        if (foundTicket.getPerson().getId() == personId) {
            TicketWrite newTicket = new TicketWrite();
            if (foundTicket.getRefundable() != null) newTicket.setDiscount(foundTicket.getDiscount());
            newTicket.setCoordinates(CoordinatesConverter.toCoordinatesWrite(foundTicket.getCoordinates()));
            newTicket.setName(foundTicket.getName());
            newTicket.setDiscount(foundTicket.getDiscount());
            newTicket.setPrice(foundTicket.getPrice() * 2);
            newTicket.setType(TicketType.VIP.name());
            newTicket.setPerson(PersonConverter.toPersonWrite(foundTicket.getPerson()));
            if (foundTicket.getRefundable() != null) newTicket.setRefundable(foundTicket.getRefundable());
            return saveTicket(newTicket);
        } else {
            messages.add("У этого билета нет владельца с данным id.");
            throw new IncorrectParameter(messages);
        }
    }


    public Object save(EventWrite dto) {
        TicketWrite ticket = new TicketWrite();

        ticket.setCoordinates(dto.getCoordinates());
        ticket.setPrice(dto.getPrice());
        ticket.setName(dto.getTitle());
        ticket.setDiscount(dto.getDiscount());


        Object object = saveTickets(ticket, dto.getTicketsNum());
        List<Ticket> tickets = new ArrayList<>();
        String str = "";
        if (object instanceof String) str = (String) object;
        else tickets = (List<Ticket>) object;

        if (!str.isEmpty()) return str;
        Event event = EventConverter.toEvent(dto);
        event.setTickets(tickets);
        em.persist(event);
        return event;
    }


    public Object saveTicket(TicketWrite ticket) {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) return response.readEntity(Ticket.class);
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
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
                else throw new TicketServiceNotAvailable(response.readEntity(String.class));
            }
        }
        return tickets;
    }


    public Object deleteTickets(List<Integer> ids) {
        List<Ticket> tickets = new ArrayList<>();

        try (Client client = ClientBuilder.newClient()) {
            for (Integer id : ids) {
                Response response = client.target(SPRING_SERVICE_URL + "/" + id)
                        .request(MediaType.APPLICATION_JSON)
                        .delete();

                if (response.getStatus() != 204) return response.readEntity(String.class);
                else throw new TicketServiceNotAvailable(response.readEntity(String.class));
            }
        }
        return tickets;
    }


    private Ticket findTicket(int id) {
        String s = SPRING_SERVICE_URL + "/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) return response.readEntity(Ticket.class);
            else if (response.getStatus() == 404) return null;
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
    }

    private Person findPerson(int id) {
        String s = SPRING_SERVICE_URL + "/people/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) return response.readEntity(Person.class);
            else if (response.getStatus() == 404) return null;
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
    }


}