package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class TicketWrite implements Serializable {
    @CustomNotNull
    @Size(min = 1, message = "Значение не должно быть пустым.")
    private String name;

    @CustomNotNull
    @Valid
    private CoordinatesWrite coordinates;


    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    private Integer price;

    @CustomNotNull
    @DecimalMin(value = "0", message = "Значение не может быть меньше возможного 0")
    @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private Double discount;

    private Boolean refundable;

    private String type;

    @Valid
    private PersonWrite person;


    public TicketWrite() {}

    public TicketWrite(CoordinatesWrite coordinates, String name, Integer price, Double discount){
        this.coordinates = coordinates;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

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