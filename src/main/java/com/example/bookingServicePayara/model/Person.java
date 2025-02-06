package com.example.bookingServicePayara.model;


import com.example.bookingServicePayara.enums.Country;
import com.example.bookingServicePayara.enums.EyeColor;
import com.example.bookingServicePayara.enums.HairColor;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @SerializedName("id")
    private int id;

    @Column(nullable = false)
    @SerializedName("height")
    private int height;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyeColor")
    @SerializedName("eyeColor")
    private EyeColor eyeColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "hairColor", nullable = false)
    @SerializedName("hairColor")
    private HairColor hairColor;


    @Enumerated(EnumType.STRING)
    @Column
    @SerializedName("nationality")
    private Country nationality;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "location", nullable = false)
    @SerializedName("location")
    private Location location;

    public Person() {
    }

    public Person(int height, EyeColor eyeColor, HairColor hairColor, Country nationality, Location location) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person(int height, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
