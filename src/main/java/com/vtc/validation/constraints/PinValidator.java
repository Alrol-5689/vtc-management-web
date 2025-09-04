package com.vtc.validation.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PinValidator implements ConstraintValidator<Pin, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Usar @NotBlank para requerir presencia; aquí solo validamos formato
            return true;
        }
        return value.matches("\\d{4}");
    }
}

