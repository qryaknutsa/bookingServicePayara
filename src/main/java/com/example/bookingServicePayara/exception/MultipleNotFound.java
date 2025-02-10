package com.example.bookingServicePayara.exception;

import jakarta.xml.ws.WebFault;

import java.util.List;

@WebFault(name = "MultipleNotFound")
public class MultipleNotFound extends RuntimeException{
    List<String> messages;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public MultipleNotFound(List<String> messages) {
        this.messages = messages;
    }
}
