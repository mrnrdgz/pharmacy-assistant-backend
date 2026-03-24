package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.ListOffersService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.UpdateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.UpdateOfferService;
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

    public OfferController(CreateOfferService createOfferService,
                           ListOffersService listOffersService,
                           UpdateOfferService updateOfferService) {
        this.createOfferService = createOfferService;
        this.listOffersService = listOffersService;
        this.updateOfferService = updateOfferService;
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
}