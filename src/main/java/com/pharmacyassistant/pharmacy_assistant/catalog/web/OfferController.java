package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final CreateOfferService createOfferService;

    public OfferController(CreateOfferService createOfferService) {
        this.createOfferService = createOfferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@Valid @RequestBody CreateOfferRequest request) {
        return createOfferService.createOffer(request);
    }
}