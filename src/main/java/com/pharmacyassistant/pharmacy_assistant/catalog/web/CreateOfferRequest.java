package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateOfferRequest {

    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String category;

    private Set<String> tags;
}