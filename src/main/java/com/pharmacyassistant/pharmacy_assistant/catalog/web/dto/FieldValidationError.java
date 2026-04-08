package com.pharmacyassistant.pharmacy_assistant.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldValidationError {

    private String field;
    private String message;
}