package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.mapper.ZonedDateTimeAdapter;
import jakarta.validation.ValidationException;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@XmlRootElement(name = "EventWrite")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventWrite implements Serializable {
    @XmlElement
    private String title;

    @XmlElement
    private String description;

    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    @XmlElement
    private ZonedDateTime startTime;

    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    @XmlElement
    private ZonedDateTime endTime;

    @XmlElement
    private CoordinatesWrite coordinates;

    @XmlElement
    private Integer price;

    @XmlElement
    private Double discount;

    @XmlElement
    private LocationWrite location;

    @XmlElement
    private Integer ticketsNum;



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


    public void validate() throws ValidationException {
        String message = "";

        if (title == null || title.length() < 1 || title.length() > 2147483647) {
            message+=("Значение должно быть от 1 до 2147483647 символов\n");
        }

        if (startTime == null || startTime.isBefore(ZonedDateTime.now())) {
            message+=("Время начала не может быть в прошлом\n");
        }

        if (endTime == null || endTime.isBefore(ZonedDateTime.now())) {
            message+=("Время окончания не может быть в прошлом\n");
        }

        if (coordinates == null) {
            message+=("Координаты не могут быть null\n");
        } else {
            coordinates.validate();
        }

        if (price == null || price < 1 || price > 2147483647) {
            message+=("Значение не может быть меньше возможного 1 и больше возможного 2147483647\n");
        }

        if (discount == null || discount < 0 || discount > 100) {
            message+=("Значение не может быть меньше возможного 0 и больше возможного 100\n");
        }

        if (location == null) {
            message+=("Местоположение не может быть null\n");
        } else {
            location.validate();
        }

        if (ticketsNum == null || ticketsNum <= 0) {
            message+=("Значение должен быть больше нуля\n");
        }

        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
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

    public CoordinatesWrite getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesWrite coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocationWrite getLocation() {
        return location;
    }

    public void setLocation(LocationWrite location) {
        this.location = location;
    }

    public Integer getTicketsNum() {
        return ticketsNum;
    }

    public void setTicketsNum(Integer ticketsNum) {
        this.ticketsNum = ticketsNum;
    }
}
