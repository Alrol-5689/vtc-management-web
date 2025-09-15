package com.vtc.validation.validators;

import com.vtc.validation.constraints.DniNie;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniNieValidator implements ConstraintValidator<DniNie, String> {

    private static final String LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Usar @NotBlank para requerir presencia
            return true;
        }

        String normalized = value.replaceAll("[\\s-]", "").toUpperCase();

        if (normalized.matches("^[0-9]{8}[A-Z]$")) { // DNI
            String numberPart = normalized.substring(0, 8);
            char expected = LETTERS.charAt(Integer.parseInt(numberPart) % 23);
            return normalized.charAt(8) == expected;
        }

        if (normalized.matches("^[XYZ][0-9]{7}[A-Z]$")) { // NIE
            char first = normalized.charAt(0);
            String numberPart = switch (first) {
                case 'X' -> "0" + normalized.substring(1, 8);
                case 'Y' -> "1" + normalized.substring(1, 8);
                case 'Z' -> "2" + normalized.substring(1, 8);
                default -> null;
            };
            if (numberPart == null) return false;
            char expected = LETTERS.charAt(Integer.parseInt(numberPart) % 23);
            return normalized.charAt(8) == expected;
        }

        return false;
    }
}

