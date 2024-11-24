package com.example.bookingServicePayara.exception;

public class TicketServiceNotAvailable extends RuntimeException {
    public TicketServiceNotAvailable(String message) {
        super(message);
    }
}
