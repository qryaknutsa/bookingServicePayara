package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

import java.util.List;

@WebFault(name = "InvalidParameter")
public class InvalidParameter extends RuntimeException {
    List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public InvalidParameter(List<String> messages) {
        this.messages = messages;
    }
}
