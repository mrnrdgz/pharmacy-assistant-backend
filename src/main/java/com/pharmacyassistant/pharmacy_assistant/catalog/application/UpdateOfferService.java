package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.UpdateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOfferService {

    private final IOfferRepository offerRepository;

    public UpdateOfferService(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer updateOffer(Long id, UpdateOfferCommand command) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        offer.setTitle(command.getTitle());
        offer.setDescription(command.getDescription());
        offer.setPrice(command.getPrice());
        offer.setCategory(command.getCategory());
        offer.setTags(command.getTags());
        offer.setValidFrom(command.getValidFrom());
        offer.setValidTo(command.getValidTo());

        return offerRepository.save(offer);
    }
}