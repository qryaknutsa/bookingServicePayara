package com.example.bookingServicePayara.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany
    @JoinTable(
            name = "event_ticket",  // Имя таблицы-соединителя
            joinColumns = @JoinColumn(name = "event_id"),  // Колонка для связи с Event
            inverseJoinColumns = @JoinColumn(name = "ticket_id")  // Колонка для связи с Ticket
    )
    private List<Ticket> tickets = new ArrayList<>();

    public Event() {
    }

    public Event(String title, String description, List<Ticket> tickets) {
        this.title = title;
        this.description = description;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
