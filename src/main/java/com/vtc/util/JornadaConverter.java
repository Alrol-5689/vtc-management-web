package com.vtc.util;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class JornadaConverter implements AttributeConverter<Map<DayOfWeek, Duration>, String> {

    @Override
    public String convertToDatabaseColumn(Map<DayOfWeek, Duration> jornada) {
        if (jornada == null) return null;
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek dia : DayOfWeek.values()) {
            Duration dur = jornada.getOrDefault(dia, Duration.ZERO);
            String codigo = switch (dia) {
                case MONDAY -> "L";
                case TUESDAY -> "M";
                case WEDNESDAY -> "X";
                case THURSDAY -> "J";
                case FRIDAY -> "V";
                case SATURDAY -> "S";
                case SUNDAY -> "D";
            };
            long horas = dur.toHours();
            long minutos = dur.minusHours(horas).toMinutes();
            sb.append(codigo).append("{").append(horas).append(":").append(minutos).append("}");
        }
        return sb.toString();
    }

    @Override
    public Map<DayOfWeek, Duration> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return new EnumMap<>(DayOfWeek.class);
        Map<DayOfWeek, Duration> jornada = new EnumMap<>(DayOfWeek.class);
        for (String token : dbData.split("(?<=})")) { // separa por cierre de llave
            if (!token.matches("[LMXJVSDF]\\{\\d+:\\d+}")) continue;
            String letra = token.substring(0, 1);
            String[] hm = token.substring(2, token.length() - 1).split(":");
            long horas = Long.parseLong(hm[0]);
            long minutos = Long.parseLong(hm[1]);
            DayOfWeek dia = switch (letra) {
                case "L" -> DayOfWeek.MONDAY;
                case "M" -> DayOfWeek.TUESDAY;
                case "X" -> DayOfWeek.WEDNESDAY;
                case "J" -> DayOfWeek.THURSDAY;
                case "V" -> DayOfWeek.FRIDAY;
                case "S" -> DayOfWeek.SATURDAY;
                case "D" -> DayOfWeek.SUNDAY;
                default -> null;
            };
            if (dia != null) jornada.put(dia, Duration.ofHours(horas).plusMinutes(minutos));
        }
        return jornada;
    }
}
