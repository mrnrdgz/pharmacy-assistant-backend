package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Getter
@Setter
public class UpdateOfferRequest {
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
