package com.example.bookingServicePayara.model;


import com.example.bookingServicePayara.model.tools.ZonedDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, columnDefinition="TEXT")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "startTime", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("startTime")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime startTime;

    @Column(name = "endTime", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("endTime")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime endTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount",nullable = false)
    @JsonProperty("discount")
    private Double discount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "event_ticket",  // Имя таблицы-соединителя
            joinColumns = @JoinColumn(name = "event_id"),  // Колонка для связи с Event
            inverseJoinColumns = @JoinColumn(name = "ticket_id")  // Колонка для связи с Ticket
    )

    private List<Ticket> tickets = new ArrayList<>();

    public Event() {
    }

    public Event(String title, String description, ZonedDateTime startTime, ZonedDateTime endTime, Coordinates coordinates, Integer price, Double discount) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
