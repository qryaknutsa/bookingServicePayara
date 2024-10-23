package com.example.bookingServicePayara.enums;


public enum TicketType {
    CHEAP("CHEAP"),
    BUDGETARY("BUDGETARY"),
    USUAL("USUAL"),
    VIP("VIP");

    private final String value;

    TicketType(String value) {
        this.value = value;
    }

}
