package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.CreateOfferRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateOfferService {

    private final IOfferRepository offerRepository;

    public CreateOfferService(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer createOffer(CreateOfferRequest request) {
        Offer offer = Offer.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .tags(request.getTags())
                .build();

        return offerRepository.save(offer);
    }
}
