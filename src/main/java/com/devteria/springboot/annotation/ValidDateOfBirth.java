package com.devteria.springboot.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = DateOfBirthValidator.class)
public @interface ValidDateOfBirth {
    int minAge() default 18;

    String message() default "INVALID_DATE_OF_BIRTH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
