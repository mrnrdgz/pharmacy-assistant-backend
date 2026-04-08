package com.pharmacyassistant.pharmacy_assistant.catalog.web.mapper;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.UpdateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.UpdateOfferRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateOfferRequestMapper {

    public static UpdateOfferCommand toCommand(UpdateOfferRequest request) {
        return new UpdateOfferCommand(
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