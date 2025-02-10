package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

@WebFault(name = "CustomNotFound")
public class CustomNotFound extends RuntimeException {
    public CustomNotFound(String message) {
        super(message);
    }
}
