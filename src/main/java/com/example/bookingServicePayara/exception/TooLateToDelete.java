package com.example.bookingServicePayara.exception;

public class TooLateToDelete extends RuntimeException {
    public TooLateToDelete(String message) {
        super(message);
    }
}
