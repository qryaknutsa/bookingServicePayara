package com.example.bookingServicePayara.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name = "coordinates")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Serializable {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("id")
    @XmlElement
    private int id;

    @Column
    @SerializedName("x")
    @XmlElement
    private float x;

    @Column(nullable = false)
    @SerializedName("y")
    @XmlElement
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
