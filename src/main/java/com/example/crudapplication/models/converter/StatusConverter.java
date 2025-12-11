package com.example.crudapplication.models.converter;

import com.example.crudapplication.models.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status attribute) {
        if (attribute == null) {
            return null;
        }
        return switch (attribute) {
            case PENDING -> "pending";
            case IN_PROGRESS -> "in-progress";
            case COMPLETED -> "completed";
        };
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return switch (dbData) {
            case "pending" -> Status.PENDING;
            case "in-progress" -> Status.IN_PROGRESS;
            case "completed" -> Status.COMPLETED;
            default -> throw new IllegalArgumentException("Unknown status: " + dbData);
        };
    }
}
