package com.example.bookingServicePayara.exception;

public class TicketSavingException extends RuntimeException {
    private Object obj;
    public TicketSavingException(Object obj) {
        this.obj = obj;
    }
}
