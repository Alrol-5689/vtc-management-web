package com.vtc.util;

import java.time.YearMonth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class YearMonthToStringConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(YearMonth yearMonth) {
        return (yearMonth == null) ? null : yearMonth.toString(); // formato "yyyy-MM"
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : YearMonth.parse(dbData);
    }
}