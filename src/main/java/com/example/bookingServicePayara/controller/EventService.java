package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dto.EventWrite;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;


@WebService
public interface EventService {

    @WebMethod
    Response getQwe();

    @WebMethod
    Response getAllEvents();

    @WebMethod
    Response getEvent(@WebParam(name = "id") String id);

    @WebMethod
    Response addEvent(@Valid @WebParam(name = "eventWrite") EventWrite dto);

    @WebMethod
    Response copyTicketWithDoublePriceAndVip(
            @WebParam(name = "ticket_id") String ticketId,
            @WebParam(name = "person_id") String personId
    );

    @WebMethod
    Response deleteEvent(@WebParam(name = "event_id") String event_id);

}
