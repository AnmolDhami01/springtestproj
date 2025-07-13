package com.newSpring.testApp.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FORMATTER_ALT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Pattern PATTERN_DATETIME_T = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
    private static final Pattern PATTERN_DATETIME_SPACE = Pattern
            .compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    private static final Pattern PATTERN_DATE_ONLY = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String dateStr = p.getText().trim();

            if (dateStr.isEmpty()) {
                return null;
            }

            if (PATTERN_DATETIME_T.matcher(dateStr).matches()) {
                return LocalDateTime.parse(dateStr, DATETIME_FORMATTER);
            } else if (PATTERN_DATETIME_SPACE.matcher(dateStr).matches()) {
                return LocalDateTime.parse(dateStr, DATETIME_FORMATTER_ALT);
            } else if (PATTERN_DATE_ONLY.matcher(dateStr).matches()) {
                LocalDate date = LocalDate.parse(dateStr, DATE_FORMATTER);
                return LocalDateTime.of(date, LocalTime.MIDNIGHT);
            } else {
                throw new IOException("Unable to parse date: " + dateStr +
                        ". Supported formats: yyyy-MM-dd, yyyy-MM-dd'T'HH:mm:ss, yyyy-MM-dd HH:mm:ss");
            }

        } catch (DateTimeParseException e) {
            throw new IOException("Unable to parse date: " + p.getText().trim() +
                    ". Supported formats: yyyy-MM-dd, yyyy-MM-dd'T'HH:mm:ss, yyyy-MM-dd HH:mm:ss", e);
        }
    }
}
