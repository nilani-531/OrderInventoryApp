package com.sprintProject.orderinventoryapplication.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sprintProject.orderinventoryapplication.entity.ShipmentStatus;
import java.io.IOException;

/**
 * Custom deserializer for ShipmentStatus enum.
 * Handles both underscore (IN_TRANSIT) and hyphen (IN-TRANSIT) formats
 * for backward compatibility with existing database data.
 */
public class ShipmentStatusDeserializer extends JsonDeserializer<ShipmentStatus> {

    @Override
    public ShipmentStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // Normalize the value: convert hyphens to underscores
        String normalized = value.toUpperCase().replace("-", "_");
        
        try {
            return ShipmentStatus.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid ShipmentStatus value: " + value, e);
        }
    }
}


