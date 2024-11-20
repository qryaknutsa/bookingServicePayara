package com.example.bookingServicePayara.model;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



@Entity
@Table(name = "location")
//@Getter
//@Setter
public class Location implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @CustomNotNull
    @Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @Column(nullable = false)    @JsonProperty("x")
    private Integer x;

    @DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808")
    @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807")
    @Column
    @JsonProperty("y")
    private long y;

    @CustomNotNull
    @DecimalMin(value = "4.9E-324", message = "Значение не может быть меньше возможного 4.9E-324")
    @DecimalMax(value = "1.7976931348623157E308", message = "Значение не может быть больше возможного 1.7976931348623157E308")
    @ValidFraction(fraction = 6, message = "Значение должно иметь не более 6 знаков после запятой.")
    @Column(nullable = false)    @JsonProperty("z")
    private Double z;

    @Size(message = "Значение должно быть до 2147483647 символов")
    @Column
    @JsonProperty("name")
    private String name;

    public Location() {
    }

    public Location(int x, long y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
