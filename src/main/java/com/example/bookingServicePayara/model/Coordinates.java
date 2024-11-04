package com.example.bookingServicePayara.model;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "coordinates")
//@Getter
//@Setter
public class Coordinates implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column
    @DecimalMin(value = "1.4E-45", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "3.4028235E38", message = "Значение не может быть больше возможного 3.4028235E38.")
    @JsonProperty("x")
    private float x;

    @CustomNotNull
    @Column(nullable = false)
    @DecimalMin(value = "1.4E-45", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "3.4028235E38", message = "Значение не может быть больше возможного 3.4028235E38.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    @JsonProperty("y")
    private Float y;

    public Coordinates() {
    }

    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
