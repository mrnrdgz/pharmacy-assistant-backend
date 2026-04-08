package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListOffersService {

    private final IOfferRepository offerRepository;

    public List<OfferResponse> list() {
        return offerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private OfferResponse toResponse(Offer offer) {
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