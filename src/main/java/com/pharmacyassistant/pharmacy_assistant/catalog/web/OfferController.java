package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.ListOffersService;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final CreateOfferService createOfferService;
    private final ListOffersService listOffersService;

    public OfferController(CreateOfferService createOfferService,
                           ListOffersService listOffersService) {
        this.createOfferService = createOfferService;
        this.listOffersService = listOffersService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@Valid @RequestBody CreateOfferRequest request) {
        return createOfferService.createOffer(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> listOffers() {
        return listOffersService.list();
    }
}