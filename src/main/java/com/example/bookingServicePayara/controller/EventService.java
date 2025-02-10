package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dto.EventRead;
import com.example.bookingServicePayara.dto.EventWrite;
import com.example.bookingServicePayara.model.Event;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import jakarta.xml.soap.SOAPException;

import java.util.List;


@WebService
public interface EventService {

    @WebMethod
    String getQwe();

    @WebMethod
    Object getAllEvents() throws SOAPException;

    @WebMethod
    EventRead getEvent(@WebParam(name = "id") String id) throws SOAPException;

    @WebMethod
    Object addEvent(@Valid @WebParam(name = "eventWrite") EventWrite dto) throws SOAPException;

    @WebMethod
    Object copyTicketWithDoublePriceAndVip(
            @WebParam(name = "ticket_id") String ticketId,
            @WebParam(name = "person_id") String personId
    ) throws SOAPException;

    @WebMethod
    Object deleteEvent(@WebParam(name = "event_id") String event_id) throws SOAPException;

}
