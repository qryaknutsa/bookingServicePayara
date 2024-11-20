package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import com.example.bookingServicePayara.validation.annotation.ValidFraction;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.io.Serializable;

public class CoordinatesWrite implements Serializable {
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 3.4028235E38.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private float x = 0;

    @CustomNotNull
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private Float y;


    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 3.4028235E38.")
    public float getX() {
        return x;
    }

    public void setX(@DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного 1.4E-45.") @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 3.4028235E38.") float x) {
        this.x = x;
    }

    public @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.") @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.") Float getY() {
        return y;
    }

    public void setY(@DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.") @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.") Float y) {
        this.y = y;
    }

    public CoordinatesWrite(){}

    public CoordinatesWrite(float x, Float y){
        this.x = x;
        this.y = y;
    }
}