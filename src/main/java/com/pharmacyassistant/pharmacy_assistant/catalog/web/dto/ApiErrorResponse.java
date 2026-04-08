package com.pharmacyassistant.pharmacy_assistant.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private int status;
    private String message;
    private List<FieldValidationError> errors;
}