package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

import java.util.List;

@WebFault(name = "IncorrectParameter")
public class IncorrectParameter extends RuntimeException {
    List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public IncorrectParameter(List<String> messages) {
        this.messages = messages;
    }
}
