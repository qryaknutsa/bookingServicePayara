package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "TooLateToDelete")
public class TooLateToDelete extends RuntimeException {
    public TooLateToDelete(String message) {
        super(message);
    }
}
