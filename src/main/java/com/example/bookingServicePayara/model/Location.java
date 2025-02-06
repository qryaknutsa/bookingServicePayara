package com.example.bookingServicePayara.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

import java.io.Serializable;



@Entity
@Table(name = "location")
public class Location implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("id")
    private int id;

    @Column(nullable = false)
    @SerializedName("x")
    private Integer x;

    @Column
    @SerializedName("y")
    private long y;

    @Column(nullable = false)
    @SerializedName("z")
    private Double z;

    @Column(columnDefinition="TEXT")
    @SerializedName("name")
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
