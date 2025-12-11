package com.example.crudapplication.models.converter;

import com.example.crudapplication.models.Priority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PriorityConverter implements AttributeConverter<Priority, String> {
    public String convertToDatabaseColumn(Priority attribute) {
        if (attribute == null) {
            return null;
        }
        return switch (attribute) {
            case LOW -> "low";
            case MEDIUM -> "medium";
            case HIGH -> "high";
        };
    }

    public Priority convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return switch (dbData) {
            case "low" -> Priority.LOW;
            case "medium" -> Priority.MEDIUM;
            case "high" -> Priority.HIGH;
            default -> throw new IllegalArgumentException("Unknown priority");
        };
    }
}
