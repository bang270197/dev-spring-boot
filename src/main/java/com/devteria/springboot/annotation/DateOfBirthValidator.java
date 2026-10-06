package com.devteria.springboot.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {
    private int minAge = 18;

    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
        minAge = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        return dateOfBirth == null || !dateOfBirth.plusYears(minAge).isAfter(LocalDate.now());
    }
}
