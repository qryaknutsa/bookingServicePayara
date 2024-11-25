package com.example.bookingServicePayara.enums.tools;

import com.example.bookingServicePayara.enums.TicketType;
import jakarta.json.bind.adapter.JsonbAdapter;

public class TicketTypeAdapter implements JsonbAdapter<TicketType, String> {
    @Override
    public TicketType adaptFromJson(String value) {
        if (value == null) {
            return null;
        }
        try {
            return TicketType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String adaptToJson(TicketType ticketType) {
        return ticketType != null ? ticketType.name() : null;
    }
}

