package com.example.bookingServicePayara.validation.annotation;

import com.example.bookingServicePayara.validation.validator.CustomNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomNotNullValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNotNull {

    String message() default "Поле не должно быть null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
