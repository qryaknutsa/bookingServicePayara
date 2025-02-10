package com.example.bookingServicePayara.dto;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "LocationWrite")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationWrite implements Serializable {
    @CustomNotNull
    @Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @XmlTransient
    private Integer x;

    @DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808")
    @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807")
    @XmlTransient
    private long y = 0;

    @CustomNotNull
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308")
    @XmlTransient
    private Double z;

    @Size(message = "Значение должно быть до 2147483647 символов")
    @XmlTransient
    private String name;







    public @Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer getX() {
        return x;
    }

    public void setX(@Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648") @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647") Integer x) {
        this.x = x;
    }

    @DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808")
    @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807")
    public long getY() {
        return y;
    }

    public void setY(@DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808") @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807") long y) {
        this.y = y;
    }

    public @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308") @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308") Double getZ() {
        return z;
    }

    public void setZ(@DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308") @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308") Double z) {
        this.z = z;
    }

    public @Size(message = "Значение должно быть до 2147483647 символов") String getName() {
        return name;
    }

    public void setName(@Size(message = "Значение должно быть до 2147483647 символов") String name) {
        this.name = name;
    }
}