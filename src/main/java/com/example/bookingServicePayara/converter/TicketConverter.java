package com.example.bookingServicePayara.converter;


import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.model.Ticket;

public class TicketConverter {


    public static Ticket toTicket(TicketWrite ticketWrite){
        Ticket ticket = new Ticket();
        ticket.setName(ticketWrite.getName());
        ticket.setPrice(ticketWrite.getPrice());
        if(ticketWrite.getType() != null) ticket.setType(TicketType.valueOf(ticketWrite.getType()));
        ticket.setDiscount(ticketWrite.getDiscount());
        if(ticketWrite.getRefundable() != null) ticket.setRefundable(ticketWrite.getRefundable());
        else ticket.setRefundable(false);
        ticket.setCoordinates(CoordinatesConverter.toCoordinates(ticketWrite.getCoordinates()));
        if(ticketWrite.getPerson() != null) ticket.setPerson(PersonConverter.toPerson(ticketWrite.getPerson()));
        return ticket;
    }



    public static TicketWrite toTicketWrite(Ticket ticket){
        TicketWrite ticketWrite = new TicketWrite();
        ticketWrite.setName(ticket.getName());
        ticketWrite.setPrice(ticket.getPrice());
        if(ticket.getType() != null) ticketWrite.setType(ticket.getType().name());
        ticketWrite.setDiscount(ticket.getDiscount());
        ticketWrite.setRefundable(ticket.isRefundable());
        ticketWrite.setCoordinates(CoordinatesConverter.toCoordinatesWrite(ticket.getCoordinates()));
        if(ticket.getPerson() != null) ticketWrite.setPerson(PersonConverter.toPersonWrite(ticket.getPerson()));
        return ticketWrite;
    }

}
