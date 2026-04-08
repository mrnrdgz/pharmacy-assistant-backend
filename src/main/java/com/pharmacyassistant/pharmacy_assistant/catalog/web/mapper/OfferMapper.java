package com.pharmacyassistant.pharmacy_assistant.catalog.web.mapper;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.OfferResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OfferMapper {

    public static OfferResponse toResponse(Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getPrice(),
                offer.getCategory(),
                offer.getTags(),
                offer.getActive(),
                offer.getValidFrom(),
                offer.getValidTo()
        );
    }
}