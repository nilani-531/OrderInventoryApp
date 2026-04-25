package com.sprintProject.OrderInventoryApplication.serialization;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Custom Hibernate converter for ShipmentStatus enum.
 * Handles both underscore (IN_TRANSIT) and hyphen (IN-TRANSIT) formats
 * for backward compatibility with existing database data.
 */
@Converter(autoApply = true)
public class ShipmentStatusType implements AttributeConverter<ShipmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ShipmentStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public ShipmentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        
        // Normalize the value: convert hyphens to underscores
        String normalized = dbData.toUpperCase().replace("-", "_");
        
        try {
            return ShipmentStatus.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            // Log the error and return null
            System.err.println("Invalid ShipmentStatus value in database: " + dbData);
            return null;
        }
    }
}

