package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisableOfferServiceTest {

    @Mock
    private IOfferRepository offerRepository;

    @InjectMocks
    private DisableOfferService disableOfferService;

    @Test
    void shouldDisableOffer() {
        // GIVEN
        Long offerId = 1L;

        Offer existingOffer = Offer.builder()
                .id(offerId)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(true)
                .build();

        Offer savedOffer = Offer.builder()
                .id(offerId)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(false)
                .build();

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(existingOffer));
        when(offerRepository.save(any(Offer.class))).thenReturn(savedOffer);

        // WHEN
        Offer result = disableOfferService.disableOffer(offerId);

        // THEN
        assertNotNull(result);
        assertEquals(offerId, result.getId());
        assertFalse(result.getActive());

        verify(offerRepository, times(1)).findById(offerId);

        ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
        verify(offerRepository, times(1)).save(captor.capture());

        Offer offerToSave = captor.getValue();
        assertFalse(offerToSave.getActive());
    }

    @Test
    void shouldThrowExceptionWhenOfferDoesNotExist() {
        // GIVEN
        Long offerId = 99L;
        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // WHEN + THEN
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> disableOfferService.disableOffer(offerId));

        assertEquals("Offer not found", exception.getMessage());

        verify(offerRepository, times(1)).findById(offerId);
        verify(offerRepository, never()).save(any(Offer.class));
    }
}