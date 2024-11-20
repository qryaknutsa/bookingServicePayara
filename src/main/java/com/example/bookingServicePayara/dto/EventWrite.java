package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.model.Coordinates;
import com.example.bookingServicePayara.model.tools.ZonedDateTimeConverter;
import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class EventWrite implements Serializable {
    @CustomNotNull
    @Size(min = 1, message = "Значение должно быть от 1 до 2147483647 символов")
    private String title;

    private String description;

    @CustomNotNull
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("startTime")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime startTime;

    @CustomNotNull
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("endTime")
    @Convert(converter = ZonedDateTimeConverter.class) // Применение конвертера
    private ZonedDateTime endTime;

    @CustomNotNull
    @Valid
    private CoordinatesWrite coordinates;

    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    private Integer price;

    @CustomNotNull
    @DecimalMin(value = "4.9E-324", message = "Значение не может быть меньше возможного 4.9E-324")
    @DecimalMax(value = "1.7976931348623157E308", message = "Значение не может быть больше возможного 1.7976931348623157E308")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private Double discount;


    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @JsonProperty("ticketsNum")
    private Integer ticketsNum;


    public @Size(min = 1, message = "Значение должно быть от 1 до 2147483647 символов") String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 1, message = "Значение должно быть от 1 до 2147483647 символов") String title) {
        this.title = title;
    }

    public EventWrite() {
    }

    public EventWrite(String title, ZonedDateTime startTime, ZonedDateTime endTime, CoordinatesWrite coordinates, Integer price, Double discount, Integer ticketsNum) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.ticketsNum = ticketsNum;
    }

    public EventWrite(String title, String description, ZonedDateTime startTime, ZonedDateTime endTime, CoordinatesWrite coordinates, Integer price, Double discount) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.ticketsNum = ticketsNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public @DecimalMin(value = "4.9E-324", message = "Значение не может быть меньше возможного 4.9E-324") @DecimalMax(value = "1.7976931348623157E308", message = "Значение не может быть больше возможного 1.7976931348623157E308") Double getDiscount() {
        return discount;
    }

    public void setDiscount(@DecimalMin(value = "4.9E-324", message = "Значение не может быть меньше возможного 4.9E-324") @DecimalMax(value = "1.7976931348623157E308", message = "Значение не может быть больше возможного 1.7976931348623157E308") Double discount) {
        this.discount = discount;
    }

    public @Positive(message = "Значение должен быть больше нуля") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer getTicketsNum() {
        return ticketsNum;
    }

    public void setTicketsNum(@Positive(message = "Значение должен быть больше нуля") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer ticketsNum) {
        this.ticketsNum = ticketsNum;
    }
}
