package com.example.bookingServicePayara.model;


import com.example.bookingServicePayara.enums.Country;
import com.example.bookingServicePayara.enums.EyeColor;
import com.example.bookingServicePayara.enums.HairColor;
import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "person")
//@Getter
//@Setter
public class Person implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @CustomNotNull
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    @Column(nullable = false)    @JsonProperty("height")
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyeColor")
    @JsonProperty("eyeColor")
    private EyeColor eyeColor;

    @CustomNotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hairColor", nullable = false)
    @JsonProperty("hairColor")
    private HairColor hairColor;


    @Enumerated(EnumType.STRING)
    @Column
    @JsonProperty("nationality")
    private Country nationality;

    @CustomNotNull
    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST) // more than one people per location
    @JoinColumn(name = "location", nullable = false)
    @JsonProperty("location")
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
