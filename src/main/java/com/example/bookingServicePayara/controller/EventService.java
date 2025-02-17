package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dto.EventReadList;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.dto.TicketWithEventWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Person;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;

import java.security.InvalidParameterException;


@WebService
public interface EventService {

    @WebMethod
    String getQwe();

    @WebMethod
    EventReadList getAllEvents() throws NotFoundException;

    @WebMethod
    EventRead getEvent(@WebParam(name = "id") String id) throws NotFoundException, InvalidParameterException;

    @WebMethod
    Event addEvent(@Valid @WebParam(name = "eventWrite") EventWrite dto) throws TicketServiceNotAvailable, ValidationException;

    @WebMethod
    Ticket copyTicketWithDoublePriceAndVip(
            @WebParam(name = "ticket_id") String ticketId,
            @WebParam(name = "person_id") String personId
    ) throws InvalidParameterException, NotFoundException, AlreadyVIPException,  TicketServiceNotAvailable;

    @WebMethod
    void deleteEvent(@WebParam(name = "event_id") String event_id) throws InvalidParameterException, TooLateToDelete, NotFoundException;

    @WebMethod
    Person getPerson(@WebParam(name = "id") String id) throws InvalidParameterException, NotFoundException;

    @WebMethod
    TicketWithEventWrite getTicket(@WebParam(name = "id") String id) throws InvalidParameterException, NotFoundException;

}
