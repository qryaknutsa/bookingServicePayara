package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "AlreadyVIPException")
public class AlreadyVIPException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Этот билет уже имеет тип VIP.";
    }
}
