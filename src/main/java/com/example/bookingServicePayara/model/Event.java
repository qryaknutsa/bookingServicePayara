package com.example.bookingServicePayara.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
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

//    @Column(name = "startTime", nullable = false)
//    private ZonedDateTime startTime;
//
//    @Column(name = "endTime", nullable = false)
//    private ZonedDateTime endTime;
//
//    @ManyToOne(cascade = CascadeType.PERSIST) // more than one events per location
//    @JoinColumn(name = "location", nullable = false)
//    private Location location;


    @OneToMany(cascade = CascadeType.PERSIST)
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
}
