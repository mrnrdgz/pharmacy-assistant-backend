package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateOfferService {

    private final IOfferRepository offerRepository;

    public CreateOfferService(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer createOffer(CreateOfferCommand command) {
        Offer offer = Offer.builder()
                .title(command.getTitle())
                .description(command.getDescription())
                .price(command.getPrice())
                .category(command.getCategory())
                .tags(command.getTags())
                .build();

        return offerRepository.save(offer);
    }
}