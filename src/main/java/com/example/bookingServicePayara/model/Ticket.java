package com.example.bookingServicePayara.model;

import com.example.bookingServicePayara.enums.TicketType;
import com.example.bookingServicePayara.enums.tools.TicketTypeAdapter;
import com.example.bookingServicePayara.model.tools.ZonedDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.*;


import java.io.Serializable;
import java.time.ZonedDateTime;


//@Data
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST) // more than one ticket to one pair of coordinates
    @JoinColumn(name = "coordinates", nullable = false)
    @JsonProperty("coordinates")
    private Coordinates coordinates;


    @Column(name = "creationDate", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("creationDate")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime creationDate = ZonedDateTime.now();


    @Column(name = "price", nullable = false)
    @JsonProperty("price")
    private int price;

    @Column(name = "discount", nullable = false)
    @JsonProperty("discount")
    private double discount;

    @Column(name = "refundable")
    @JsonProperty("refundable")
    private Boolean refundable;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @JsonProperty("type")
    @JsonbTypeAdapter(TicketTypeAdapter.class)
    private TicketType type;

    @ManyToOne(cascade = CascadeType.PERSIST) // more than one ticket to one person
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
