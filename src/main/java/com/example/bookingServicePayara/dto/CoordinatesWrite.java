package com.example.bookingServicePayara.dto;

import jakarta.validation.ValidationException;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "CoordinatesWrite")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoordinatesWrite", propOrder = {"x", "y"}) // Добавляем, чтобы определить порядок элементов
public class CoordinatesWrite implements Serializable {
    @XmlElement
    private float x = 0;

    @XmlElement
    private Float y;


    public void validate() throws ValidationException {
        String message = "";

        if (x < -1.79769313348623157E308 || x > 1.79769313348623157E308) {
            message += ("Значение x не может быть меньше возможного -1.79769313348623157E308 и больше возможного 1.79769313348623157E308.\n");
        }

        if (y == null) {
            message += ("Значение y не может быть null.\n");
        } else if (y < -1.79769313348623157E308 || y > 1.79769313348623157E308) {
            message += ("Значение y не может быть меньше возможного -1.79769313348623157E308 и больше возможного 1.79769313348623157E308.\n");
        }

        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public CoordinatesWrite() {
    }

    public CoordinatesWrite(float x, Float y) {
        this.x = x;
        this.y = y;
    }

}