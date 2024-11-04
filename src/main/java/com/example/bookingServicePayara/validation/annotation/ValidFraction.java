package com.example.bookingServicePayara.validation.annotation;

import com.example.bookingServicePayara.validation.validator.FractionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FractionValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFraction {
    String message() default "Должно быть округлено";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int fraction() default 2; // Максимальное количество знаков после запятой
}
