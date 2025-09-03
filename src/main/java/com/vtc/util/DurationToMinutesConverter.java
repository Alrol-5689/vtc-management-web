package com.vtc.util;

import java.time.Duration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class DurationToMinutesConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration == null) ? null : duration.toMinutes();
    }

    @Override
    public Duration convertToEntityAttribute(Long minutes) {
        return (minutes == null) ? null : Duration.ofMinutes(minutes);
    }
}
