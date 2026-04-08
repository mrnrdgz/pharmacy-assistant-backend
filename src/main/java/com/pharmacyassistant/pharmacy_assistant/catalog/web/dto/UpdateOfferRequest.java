package com.pharmacyassistant.pharmacy_assistant.catalog.web.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfferRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must not exceed 150 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Column(nullable = false, length = 50)
    private String category;

    private Set<@NotBlank(message = "Tags must not be blank")
    @Size(max = 30, message = "Each tag must not exceed 30 characters")
            String> tags;

    private LocalDate validFrom;
    private LocalDate validTo;

}
