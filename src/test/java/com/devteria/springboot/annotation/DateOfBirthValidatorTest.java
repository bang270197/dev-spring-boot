package com.devteria.springboot.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateOfBirthValidatorTest {
    private final DateOfBirthValidator validator = new DateOfBirthValidator();

    @Test
    void acceptsMissingDateAndAdults() {
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid(LocalDate.now().minusYears(18), null));
        assertTrue(validator.isValid(LocalDate.now().minusYears(20), null));
    }

    @Test
    void rejectsUsersUnder18() {
        assertFalse(validator.isValid(LocalDate.now().minusYears(18).plusDays(1), null));
        assertFalse(validator.isValid(LocalDate.now().plusDays(1), null));
    }

    @Test
    void usesConfiguredMinimumAge() {
        validator.initialize(annotationWithMinimumAge(21));

        assertFalse(validator.isValid(LocalDate.now().minusYears(20), null));
        assertTrue(validator.isValid(LocalDate.now().minusYears(21), null));
    }

    private ValidDateOfBirth annotationWithMinimumAge(int minAge) {
        return new ValidDateOfBirth() {
            @Override
            public int minAge() {
                return minAge;
            }

            @Override
            public String message() {
                return "";
            }

            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return ValidDateOfBirth.class;
            }
        };
    }
}
