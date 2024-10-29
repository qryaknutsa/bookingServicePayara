package com.example.bookingServicePayara.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



@Entity
@Table(name = "location")
//@Getter
//@Setter
public class Location implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Column(name = "x", nullable = false)
    @JsonProperty("x")
    private int x;

    @Column(name = "y")
    @JsonProperty("y")
    private double y;

    @Column(name = "z", nullable = false)
    @JsonProperty("z")
    private double z;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    public Location() {
    }

    public Location(int x, double y, double z, String name) {
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

    public double getY() {
        return y;
    }

    public void setY(double y) {
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
