package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.UpdateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.UpdateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOfferServiceTest {

    @Mock
    private IOfferRepository offerRepository;

    @InjectMocks
    private UpdateOfferService updateOfferService;

    @Test
    void shouldUpdateOffer() {

        // GIVEN
        Long offerId = 1L;

        UpdateOfferCommand command = new UpdateOfferCommand(
                "Updated Perfume",
                "Updated Desc",
                new BigDecimal("15000"),
                "updated-category",
                Set.of("updated", "promo"),
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 30)
        );

        Offer existingOffer = Offer.builder()
                .id(offerId)
                .title("Old Perfume")
                .description("Old Desc")
                .price(new BigDecimal("10000"))
                .category("old-category")
                .tags(Set.of("old"))
                .active(true)
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
                .build();

        Offer savedOffer = Offer.builder()
                .id(offerId)
                .title("Updated Perfume")
                .description("Updated Desc")
                .price(new BigDecimal("15000"))
                .category("updated-category")
                .tags(Set.of("updated", "promo"))
                .active(true)
                .validFrom(LocalDate.of(2025, 4, 1))
                .validTo(LocalDate.of(2025, 4, 30))
                .build();

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepository.save(any(Offer.class))).thenReturn(savedOffer);

        // WHEN
        Offer result = updateOfferService.updateOffer(offerId, command);

        // THEN
        assertNotNull(result);
        assertEquals(offerId, result.getId());
        assertEquals("Updated Perfume", result.getTitle());
        assertEquals("Updated Desc", result.getDescription());
        assertEquals(new BigDecimal("15000"), result.getPrice());
        assertEquals("updated-category", result.getCategory());
        assertEquals(Set.of("updated", "promo"), result.getTags());
        assertEquals(LocalDate.of(2025, 4, 1), result.getValidFrom());
        assertEquals(LocalDate.of(2025, 4, 30), result.getValidTo());

        verify(offerRepository, times(1)).findById(offerId);

        ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
        verify(offerRepository, times(1)).save(captor.capture());

        Offer offerToSave = captor.getValue();
        assertEquals("Updated Perfume", offerToSave.getTitle());
        assertEquals("Updated Desc", offerToSave.getDescription());
        assertEquals(new BigDecimal("15000"), offerToSave.getPrice());
        assertEquals("updated-category", offerToSave.getCategory());
        assertEquals(Set.of("updated", "promo"), offerToSave.getTags());
        assertEquals(LocalDate.of(2025, 4, 1), offerToSave.getValidFrom());
        assertEquals(LocalDate.of(2025, 4, 30), offerToSave.getValidTo());
    }

    @Test
    void shouldThrowExceptionWhenOfferDoesNotExist() {

        // GIVEN
        Long offerId = 99L;

        UpdateOfferCommand command = new UpdateOfferCommand(
                "Updated Perfume",
                "Updated Desc",
                new BigDecimal("15000"),
                "updated-category",
                Set.of("updated", "promo"),
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 30)
        );

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // WHEN + THEN
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> updateOfferService.updateOffer(offerId, command));

        assertEquals("Offer not found", exception.getMessage());

        verify(offerRepository, times(1)).findById(offerId);
        verify(offerRepository, never()).save(any(Offer.class));
    }
}