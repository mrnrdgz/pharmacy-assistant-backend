package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
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