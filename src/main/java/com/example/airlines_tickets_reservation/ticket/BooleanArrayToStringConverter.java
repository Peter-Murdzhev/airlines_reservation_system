package com.example.airlines_tickets_reservation.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class BooleanArrayToStringConverter implements AttributeConverter<boolean[],String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(boolean[] isSeatTaken) {
        try {
            return objectMapper.writeValueAsString(isSeatTaken);
        }catch (JsonProcessingException jpe){
            throw new RuntimeException("Could not convert boolean arr to JSON");
        }
    }

    @Override
    public boolean[] convertToEntityAttribute(String isSeatTaken) {
        try {
            return objectMapper.readValue(isSeatTaken,boolean[].class);
        }catch (IOException ioe){
            throw new RuntimeException("Could not convert json to array");
        }
    }
}
