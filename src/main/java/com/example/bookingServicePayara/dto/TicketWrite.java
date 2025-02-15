package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketWrite implements Serializable {
    @CustomNotNull
    @Size(min = 1, message = "Значение не должно быть пустым.")
    @XmlElement
    private String name;

    @CustomNotNull
    @Valid
    @XmlElement
    private CoordinatesWrite coordinates;


    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @XmlElement
    private Integer price;

    @CustomNotNull
    @DecimalMin(value = "1", message = "Значение не может быть меньше возможного 0")
    @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100")
    @XmlElement
    private Double discount;

    @XmlElement
    private Boolean refundable = false;

    @XmlElement(name="ticketType")
    private String type;

    @Valid
    @XmlElement
    private PersonWrite person;

    public TicketWrite() {}



    public @Size(min = 1, message = "Значение не должно быть пустым.") String getName() {
        return name;
    }

    public void setName(@Size(min = 1, message = "Значение не должно быть пустым.") String name) {
        this.name = name;
    }

    public @Valid CoordinatesWrite getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@Valid CoordinatesWrite coordinates) {
        this.coordinates = coordinates;
    }

    public @Positive(message = "Значение должен быть больше нуля") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Значение должен быть больше нуля") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer price) {
        this.price = price;
    }

    public @DecimalMin(value = "0", message = "Значение не может быть меньше возможного 0") @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100") Double getDiscount() {
        return discount;
    }

    public void setDiscount(@DecimalMin(value = "0", message = "Значение не может быть меньше возможного 0") @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100") Double discount) {
        this.discount = discount;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public @Valid PersonWrite getPerson() {
        return person;
    }

    public void setPerson(@Valid PersonWrite person) {
        this.person = person;
    }
}