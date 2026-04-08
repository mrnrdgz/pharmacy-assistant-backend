package com.pharmacyassistant.pharmacy_assistant.catalog.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class OfferResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private Set<String> tags;
    private Boolean active;
    private LocalDate validFrom;
    private LocalDate validTo;
}