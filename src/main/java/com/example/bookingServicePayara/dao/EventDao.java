package com.example.bookingServicePayara.dao;

import com.example.bookingServicePayara.converter.EventConverter;
import com.example.bookingServicePayara.dto.*;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class EventDao {
    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager em;

    public EventReadList getAll() {
        try {
            List<Event> list = em.createQuery("SELECT l FROM Event l", Event.class).getResultList();
            List<EventRead> toReturn = new ArrayList<>();

            for (Event e : list) {
                EventRead eventRead = EventConverter.toEventRead(e);
                eventRead.setTicketsNum(TicketService.findTicketsByEventId(e.getId()));
                toReturn.add(eventRead);
            }
            if (toReturn.isEmpty())
                throw new NotFoundException("По вашему запросу мероприятия не найдено.");

            return new EventReadList(toReturn);
        } catch (NotFoundException e) {
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();
                fault.setFaultCode("404");
                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public EventRead getById(String idStr) {
        try {
            int id = validateId(idStr);
            Event e = em.find(Event.class, id);
            if (id < 0) throw new InvalidParameterException("Значение event_id должно быть больше нуля");
            if (e == null) throw new NotFoundException("По вашему запросу мероприятие не найдено.");
            return EventConverter.toEventRead(e);
        } catch (InvalidParameterException | NotFoundException e) {
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();

                if (e instanceof InvalidParameterException) fault.setFaultCode("400");
                if (e instanceof NotFoundException) fault.setFaultCode("404");

                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Event save(EventWrite dto) throws ValidationException {
        try {
            dto.validate();
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
                throw new TicketServiceNotAvailable(str);
            } else if (tickets.size() != dto.getTicketsNum()) {
                em.remove(event);
                throw new TicketServiceNotAvailable("Не вышло сохранить такое количество билетов");
            }
            return event;
        }catch (ValidationException | TicketServiceNotAvailable e) {
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();

                if (e instanceof ValidationException) fault.setFaultCode("422");
                if (e instanceof TicketServiceNotAvailable) fault.setFaultCode("503");

                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void delete(String eventIdStr) {
        try {
            int event_id = validateId(eventIdStr);
            if (event_id < 0) throw new InvalidParameterException("Значение event_id должно быть больше нуля");


            Event event = em.find(Event.class, event_id);
            if (event != null) {
//            if (event.getStartTime().isBefore(ZonedDateTime.now()) && event.getEndTime().isAfter(ZonedDateTime.now()))
//                throw new TooLateToDelete("Мероприятие уже началось, отменить невозможно.");
//            else if (ZonedDateTime.now().isAfter(event.getEndTime()))
//                throw new TooLateToDelete("Мероприятие уже прошло, отменить невозможно.");
//            else {
                TicketService.deleteTickets(event_id);
                em.remove(event);
//            }
            } else throw new NotFoundException("По вашему запросу мероприятие не найдено.");
        } catch (InvalidParameterException | NotFoundException | TicketServiceNotAvailable e) {
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();

                if (e instanceof InvalidParameterException) fault.setFaultCode("400");
                if (e instanceof NotFoundException) fault.setFaultCode("404");
                if (e instanceof TicketServiceNotAvailable) fault.setFaultCode("503");

                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Ticket copyTicketWithDoublePriceAndVip(String ticketIdStr, String personIdStr) {
        try {
            int ticketId = 0;
            int personId = 0;
            boolean invalidTicketId = false;
            boolean invalidPersonId = false;
            String messages = "";

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
                messages += ("Некорректное значение параметра ticket_id: " + ticketIdStr + "\n");
                messages += ("Некорректное значение параметра person_id: " + personIdStr + "\n");
                throw new InvalidParameterException(messages);
            }
            if (invalidTicketId) {
                messages += ("Некорректное значение параметра ticket_id: " + ticketIdStr + "\n");
                throw new InvalidParameterException(messages);
            }
            if (invalidPersonId) {
                messages += ("Некорректное значение параметра person_id: " + personIdStr + "\n");
                throw new InvalidParameterException(messages);
            }


            if (ticketId < 0) invalidTicketId = true;
            if (personId < 0) invalidPersonId = true;
            if (invalidTicketId && invalidPersonId) {
                messages += ("Значение ticket_id должно быть больше нуля" + "\n");
                messages += ("Значение person_id должно быть больше нуля" + "\n");
                throw new InvalidParameterException(messages);
            }
            if (invalidTicketId) {
                messages += ("Значение ticket_id должно быть больше нуля" + "\n");
                throw new InvalidParameterException(messages);
            }
            if (invalidPersonId) {
                messages += ("Значение person_id должно быть больше нуля" + "\n");
                throw new InvalidParameterException(messages);
            }


            TicketWithEventWrite foundTicket = TicketService.findTicket(ticketId);
            Person foundPerson = TicketService.findPerson(personId);

            if (foundTicket == null) invalidTicketId = true;
            if (foundPerson == null) invalidPersonId = true;
            if (invalidTicketId && invalidPersonId) {
                messages += ("Билет с данным ID не найден.");
                messages += ("Человек с данным ID не найден.");
                throw new NotFoundException(messages);
            }
            if (invalidTicketId) {
                messages += ("Билет с данным ID не найден.");
                throw new NotFoundException(messages);
            }
            if (invalidPersonId) {
                messages += ("Человек с данным ID не найден.");
                throw new NotFoundException(messages);
            }

            if (foundTicket.getPerson() == null) {
                messages += ("У билета нет владельца.");
                throw new InvalidParameterException(messages);
            }

            if (foundTicket.getPerson().getId() == personId) {
                if (foundTicket.getType() != null) {
                    if (foundTicket.getType().equals("VIP")) {
                        throw new AlreadyVIPException();
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

                Object obj = TicketService.saveTicket(newTicket);
                if (obj instanceof Ticket) return (Ticket) obj;
                else throw new TicketServiceNotAvailable((String) obj);
            } else {
                messages += ("У этого билета нет владельца с данным id.");
                throw new InvalidParameterException(messages);
            }
        } catch (InvalidParameterException | NotFoundException | TicketServiceNotAvailable e) {
            try {
                SOAPFactory factory = SOAPFactory.newInstance();
                SOAPFault fault = factory.createFault();

                if (e instanceof InvalidParameterException) fault.setFaultCode("400");
                if (e instanceof NotFoundException) fault.setFaultCode("404");
                if (e instanceof TicketServiceNotAvailable) fault.setFaultCode("503");

                fault.setFaultString(e.getMessage());
                throw new SOAPFaultException(fault);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private int validateId(String idStr) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Некорректное значение параметра ticket_id: " + idStr);
        }
        return id;
    }


}
