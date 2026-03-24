package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferCommand;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateOfferRequestMapper {
    public static CreateOfferCommand toCommand(CreateOfferRequest request) {
        return new CreateOfferCommand(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getTags()
        );
    }
}