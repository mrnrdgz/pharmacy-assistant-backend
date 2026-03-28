package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class DisableOfferService {
    private final IOfferRepository offerRepository;

    public DisableOfferService(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }
    public Offer disableOffer(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        if (!offer.getActive()) {
            return offer; // ya está deshabilitada
        }

        offer.setActive(false);
        return offerRepository.save(offer);
    }
}
