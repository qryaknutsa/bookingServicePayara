package com.example.bookingServicePayara.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "coordinates")
//@Getter
//@Setter
public class Coordinates implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(name = "x")
    @JsonProperty("x")
    private float x;

    @Column(name = "y", nullable = false)
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
