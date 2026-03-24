package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.UpdateOfferCommand;

public class UpdateOfferRequestMapper {

    private UpdateOfferRequestMapper() {
    }

    public static UpdateOfferCommand toCommand(UpdateOfferRequest request) {
        return new UpdateOfferCommand(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getTags()
        );
    }
}