package com.example.bookingServicePayara.exception;

public class TicketDeletingException extends RuntimeException {
    private Object obj;

    public TicketDeletingException(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }
}


