package com.example.bookingServicePayara.validation.validator;

import com.example.bookingServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomNotNullValidator implements ConstraintValidator<CustomNotNull, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}
