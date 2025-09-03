package com.vtc.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BooleanArrayToStringConverter implements AttributeConverter<boolean[], String> {

    @Override
    public String convertToDatabaseColumn(boolean[] array) {
        if (array == null) return null;
        StringBuilder sb = new StringBuilder();
        for (boolean b : array) sb.append(b ? "1" : "0");
        return sb.toString();
    }

    @Override
    public boolean[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() != 12) return new boolean[12];
        boolean[] array = new boolean[12];
        for (int i = 0; i < 12; i++) array[i] = dbData.charAt(i) == '1';
        return array;
    }
}
