package com.pharmacyassistant.pharmacy_assistant.catalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
public class CreateOfferCommand {

    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private Set<String> tags;
    private LocalDate validFrom;
    private LocalDate validTo;
}