package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.*;
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
    private final UpdateOfferService updateOfferService;
    private final DisableOfferService disableOfferService;

    public OfferController(CreateOfferService createOfferService,
                           ListOffersService listOffersService,
                           UpdateOfferService updateOfferService, DisableOfferService disableOfferService) {
        this.createOfferService = createOfferService;
        this.listOffersService = listOffersService;
        this.updateOfferService = updateOfferService;
        this.disableOfferService = disableOfferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferResponse createOffer(@Valid @RequestBody CreateOfferRequest request) {
        CreateOfferCommand command = CreateOfferRequestMapper.toCommand(request);
        Offer offer = createOfferService.createOffer(command);
        return OfferMapper.toResponse(offer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponse> listOffers() {
        return listOffersService.list();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponse updateOffer(@PathVariable Long id,
                                     @Valid @RequestBody UpdateOfferRequest request) {
        UpdateOfferCommand command = UpdateOfferRequestMapper.toCommand(request);
        Offer offer = updateOfferService.updateOffer(id, command);
        return OfferMapper.toResponse(offer);
    }

    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponse disableOffer(@PathVariable Long id) {
        Offer offer = disableOfferService.disableOffer(id);
        return OfferMapper.toResponse(offer);
    }
}