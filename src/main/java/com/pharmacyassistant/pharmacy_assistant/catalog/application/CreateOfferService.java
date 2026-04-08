package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.CreateOfferCommand;
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

        if (command.getValidFrom() != null
                && command.getValidTo() != null
                && command.getValidTo().isBefore(command.getValidFrom())) {
            throw new RuntimeException("validTo cannot be before validFrom");
        }
        if (offerRepository.existsByTitle(command.getTitle())) {
            throw new RuntimeException("Offer with this title already exists");
        }
        Offer offer = Offer.builder()
                .title(command.getTitle())
                .description(command.getDescription())
                .price(command.getPrice())
                .category(command.getCategory())
                .tags(command.getTags())
                .validFrom(command.getValidFrom())
                .validTo(command.getValidTo())
                .build();

        return offerRepository.save(offer);
    }
}