package com.example.bookingServicePayara.dto;

import jakarta.validation.ValidationException;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;


@XmlRootElement(name = "locationWrite")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "locationWrite", propOrder = {"x", "y", "z", "name"})
public class LocationWrite implements Serializable {
    @XmlElement
    private Integer x;

    @XmlElement
    private long y = 0;

    @XmlElement
    private Double z;

    @XmlElement
    private String name;


    public void validate() throws ValidationException {
        String message = "";

        if (x == null) {
            message+=("Значение x не может быть null.\n");
        } else if (x < -2147483648 || x > 2147483647) {
            message+=("Значение x не может быть меньше возможного -2147483648 и больше возможного 2147483647.\n");
        }

        if (y < -9223372036854775808L || y > 9223372036854775807L) {
            message+=("Значение y не может быть меньше возможного -9223372036854775808 и больше возможного 9223372036854775807.\n");
        }

        if (z == null) {
            message+=("Значение z не может быть null.");
        } else if (z < -1.79769313348623157E308 || z > 1.79769313348623157E308) {
            message+=("Значение z не может быть меньше возможного -1.79769313348623157E308 и больше возможного 1.79769313348623157E308.\n");
        }

        if (name != null && name.length() > 2147483647) {
            message+=("Значение name не может быть больше 2147483647 символов.\n");
        }

        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }


    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}