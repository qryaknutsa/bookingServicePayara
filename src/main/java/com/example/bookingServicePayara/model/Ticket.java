package com.example.bookingServicePayara.model;

import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.enums.tools.TicketTypeAdapter;
import com.example.bookingServicePayara.model.tools.ZonedDateTimeConverter;
import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.io.Serializable;
import java.time.ZonedDateTime;


@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(nullable = false, columnDefinition="TEXT")
    @JsonProperty("name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "coordinates", nullable = false)
    @JsonProperty("coordinates")
    private Coordinates coordinates;


    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("creationDate")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime creationDate = ZonedDateTime.now();


    @Column(nullable = false)
    @JsonProperty("price")
    private int price;

    @Column(nullable = false)
    @JsonProperty("discount")
    private double discount;

    @Column
    @JsonProperty("refundable")
    private Boolean refundable;

    @Enumerated(EnumType.STRING)
    @Column
    @JsonProperty("type")
    @JsonbTypeAdapter(TicketTypeAdapter.class)
    private TicketType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "person")
    @JsonProperty("person")
    private Person person;

    public Ticket() {
    }

    public Ticket(String name, Coordinates coordinates, int price, double discount, Boolean refundable, TicketType type, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.person = person;
    }

    public Ticket(String name, Coordinates coordinates, int price, double discount) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
