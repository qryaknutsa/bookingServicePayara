package com.example.bookingServicePayara.dao;

import com.example.bookingServicePayara.converter.EventConverter;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.dto.TicketWithEventWrite;
import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.exception.tools.GlobalExceptionMapper;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.xml.soap.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;

    public Object getAll() throws SOAPException {
        List<Event> list = em.createQuery("SELECT l FROM Event l", Event.class).getResultList();
        List<EventRead> toReturn = new ArrayList<>();

        for (Event e : list) {
            EventRead eventRead = EventConverter.toEventRead(e);
            eventRead.setTicketsNum(TicketService.findTicketsByEventId(e.getId()));
            toReturn.add(eventRead);
        }

        if (toReturn.isEmpty()) {
            CustomNotFound ex = new CustomNotFound("По вашему запросу мероприятия не найдено.");
            return GlobalExceptionMapper.handleException(ex);
        }
        return toReturn;
    }

    public EventRead getById(String idStr) throws SOAPException {
        int id = validateId(idStr);
        Event e = em.find(Event.class, id);
//        if (id < 0) {
//            List<String> messages = new ArrayList<>();
//            messages.add("Значение event_id должно быть больше нуля");
//            InvalidParameter ex = new InvalidParameter(messages);
//            return GlobalExceptionMapper.handleException(ex);
//        }
//        if (e == null) {
//            CustomNotFound ex = new CustomNotFound("По вашему запросу мероприятие не найдено.");
//            return GlobalExceptionMapper.handleException(ex);
//        }
        return EventConverter.toEventRead(e);
    }

    public Object save(EventWrite dto) throws SOAPException {
        Event event = EventConverter.toEvent(dto);
        em.persist(event);
        em.flush();

        TicketWithEventWrite ticket = new TicketWithEventWrite();

        ticket.setCoordinates(dto.getCoordinates());
        ticket.setPrice(dto.getPrice());
        ticket.setName(dto.getTitle());
        ticket.setDiscount(dto.getDiscount());
        ticket.setEventId(event.getId());

        Object object = TicketService.saveTickets(ticket, dto.getTicketsNum());
        List<Integer> tickets = new ArrayList<>();
        String str = "";
        if (object instanceof String) str = (String) object;
        else tickets = (List<Integer>) object;

        if (!str.isEmpty()) {
            em.remove(event);
            TicketServiceNotAvailable ex = new TicketServiceNotAvailable(str);
            return GlobalExceptionMapper.handleException(ex);
        } else if (tickets.size() != dto.getTicketsNum()) {
            em.remove(event);
            TicketServiceNotAvailable ex = new TicketServiceNotAvailable("Не вышло сохранить такое количество билетов");
            return GlobalExceptionMapper.handleException(ex);
        }
        return event;
    }

    public Object delete(String eventIdStr) throws SOAPException {
        int event_id = validateId(eventIdStr);
        if (event_id < 0) {
            List<String> messages = new ArrayList<>();
            messages.add("Значение event_id должно быть больше нуля");
            InvalidParameter ex = new InvalidParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }

        Event event = em.find(Event.class, event_id);
        if (event != null) {
            if (event.getStartTime().isBefore(ZonedDateTime.now())) {
                TooLateToDelete ex = new TooLateToDelete("Мероприятие уже началось, отменить невозможно.");
                return GlobalExceptionMapper.handleException(ex);
            } else if (ZonedDateTime.now().isAfter(event.getEndTime())) {
                TooLateToDelete ex = new TooLateToDelete("Мероприятие уже прошло, отменить невозможно.");
                return GlobalExceptionMapper.handleException(ex);
            } else {
                TicketService.deleteTickets(event_id);
                em.remove(event);
                return null;
            }
        } else {
            CustomNotFound ex = new CustomNotFound("По вашему запросу мероприятие не найдено.");
            return GlobalExceptionMapper.handleException(ex);
        }
    }

    public Object copyTicketWithDoublePriceAndVip(String ticketIdStr, String personIdStr) throws SOAPException {
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
            IncorrectParameter ex = new IncorrectParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidTicketId) {
            messages.add("Некорректное значение параметра ticket_id: " + ticketIdStr);
            IncorrectParameter ex = new IncorrectParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidPersonId) {
            messages.add("Некорректное значение параметра person_id: " + personIdStr);
            IncorrectParameter ex = new IncorrectParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }


        if (ticketId < 0) invalidTicketId = true;
        if (personId < 0) invalidPersonId = true;
        if (invalidTicketId && invalidPersonId) {
            messages.add("Значение ticket_id должно быть больше нуля");
            messages.add("Значение person_id должно быть больше нуля");
            InvalidParameter ex = new InvalidParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidTicketId) {
            messages.add("Значение ticket_id должно быть больше нуля");
            InvalidParameter ex = new InvalidParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidPersonId) {
            messages.add("Значение person_id должно быть больше нуля");
            InvalidParameter ex = new InvalidParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }


        TicketWithEventWrite foundTicket = TicketService.findTicket(ticketId);
        Person foundPerson = TicketService.findPerson(personId);

        if (foundTicket == null) invalidTicketId = true;
        if (foundPerson == null) invalidPersonId = true;
        if (invalidTicketId && invalidPersonId) {
            messages.add("Билет с данным ID не найден.");
            messages.add("Человек с данным ID не найден.");
            MultipleNotFound ex = new MultipleNotFound(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidTicketId) {
            messages.add("Билет с данным ID не найден.");
            MultipleNotFound ex = new MultipleNotFound(messages);
            return GlobalExceptionMapper.handleException(ex);
        }
        if (invalidPersonId) {
            messages.add("Человек с данным ID не найден.");
            MultipleNotFound ex = new MultipleNotFound(messages);
            return GlobalExceptionMapper.handleException(ex);
        }

        if (foundTicket.getPerson() == null) {
            messages.add("У билета нет владельца.");
            IncorrectParameter ex = new IncorrectParameter(messages);
            return GlobalExceptionMapper.handleException(ex);
        }

        if (foundTicket.getPerson().getId() == personId) {
            if (foundTicket.getType() != null) {
                if (foundTicket.getType().equals("VIP")) {
                    AlreadyVIPException ex = new AlreadyVIPException();
                    return GlobalExceptionMapper.handleException(ex);
                }
            }
            TicketWrite newTicket = new TicketWrite();
            if (foundTicket.getRefundable() != null) newTicket.setDiscount(foundTicket.getDiscount());
            newTicket.setCoordinates(foundTicket.getCoordinates());
            newTicket.setName(foundTicket.getName());
            newTicket.setDiscount(foundTicket.getDiscount());
            newTicket.setPrice(foundTicket.getPrice() * 2);
            newTicket.setType("VIP");
            newTicket.setPerson(foundTicket.getPerson());
            if (foundTicket.getRefundable() != null) newTicket.setRefundable(foundTicket.getRefundable());
            else newTicket.setRefundable(false);
            return TicketService.saveTicket(newTicket);
        } else {
            messages.add("У этого билета нет владельца с данным id.");
            throw new IncorrectParameter(messages);
        }
    }


    private int validateId(String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            List<String> messages = new ArrayList<>();
            messages.add("Некорректное значение параметра ticket_id: " + idStr);
            throw new IncorrectParameter(messages);
        }
        return id;
    }


}
