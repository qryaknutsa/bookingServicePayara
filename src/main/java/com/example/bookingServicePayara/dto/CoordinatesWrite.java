package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "CoordinatesWrite")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesWrite implements Serializable {
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    @XmlTransient
    private float x = 0;

    @CustomNotNull
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    @XmlTransient
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