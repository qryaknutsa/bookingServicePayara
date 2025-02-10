package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "TicketServiceNotAvailable")
public class TicketServiceNotAvailable extends RuntimeException {
    public TicketServiceNotAvailable(String message) {
        super(message);
    }
}
