package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dto.EventReadList;
import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.exception.*;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;


@WebService
public interface EventService {

    @WebMethod
    String getQwe();

    @WebMethod
    EventReadList getAllEvents() throws CustomNotFound;

    @WebMethod
    EventRead getEvent(@WebParam(name = "id") String id) throws CustomNotFound, InvalidParameter;

    @WebMethod
    Event addEvent(@Valid @WebParam(name = "eventWrite") EventWrite dto) throws TicketServiceNotAvailable;

    @WebMethod
    Ticket copyTicketWithDoublePriceAndVip(
            @WebParam(name = "ticket_id") String ticketId,
            @WebParam(name = "person_id") String personId
    ) throws IncorrectParameter, InvalidParameter, MultipleNotFound, AlreadyVIPException,  TicketServiceNotAvailable;

    @WebMethod
    void deleteEvent(@WebParam(name = "event_id") String event_id) throws InvalidParameter, TooLateToDelete, CustomNotFound;

}
