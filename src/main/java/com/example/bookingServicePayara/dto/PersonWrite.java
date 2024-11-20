package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

public class PersonWrite implements Serializable {

    @CustomNotNull
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    private Integer height;

    private String eyeColor;

    @CustomNotNull
    private String hairColor;

    private String nationality;

    @CustomNotNull
    @Valid
    private LocationWrite location;


    public @Min(value = 50, message = "Значение должно быть больше 50") @Max(value = 300, message = "Значение должно быть меньше 300") Integer getHeight() {
        return height;
    }

    public void setHeight(@Min(value = 50, message = "Значение должно быть больше 50") @Max(value = 300, message = "Значение должно быть меньше 300") Integer height) {
        this.height = height;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public @Valid LocationWrite getLocation() {
        return location;
    }

    public void setLocation(@Valid LocationWrite location) {
        this.location = location;
    }
}