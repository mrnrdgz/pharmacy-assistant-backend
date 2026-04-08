package com.pharmacyassistant.pharmacy_assistant.catalog.web.mapper;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.CreateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.CreateOfferRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOfferRequestMapper {

    public static CreateOfferCommand toCommand(CreateOfferRequest request) {
        return new CreateOfferCommand(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getTags(),
                request.getValidFrom(),
                request.getValidTo()
        );
    }
}