package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.CreateOfferCommand;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOfferServiceTest {

    @Mock
    private IOfferRepository offerRepository;

    @InjectMocks
    private CreateOfferService createOfferService;

    @Test
    void shouldCreateOffer() {

        // GIVEN
        CreateOfferCommand command = new CreateOfferCommand(
                "Perfume Test",
                "Desc",
                new BigDecimal("10000"),
                "perfumes",
                Set.of("test"),
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31)
        );

        Offer savedOffer = Offer.builder()
                .id(1L)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
                .build();

        when(offerRepository.save(any(Offer.class))).thenReturn(savedOffer);

        // WHEN
        Offer result = createOfferService.createOffer(command);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Perfume Test", result.getTitle());
        assertEquals(LocalDate.of(2025, 3, 1), result.getValidFrom());
        assertEquals(LocalDate.of(2025, 3, 31), result.getValidTo());

        ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
        verify(offerRepository).save(captor.capture());

        Offer offerToSave = captor.getValue();
        assertEquals("Perfume Test", offerToSave.getTitle());
        assertEquals("Desc", offerToSave.getDescription());
        assertEquals(new BigDecimal("10000"), offerToSave.getPrice());
        assertEquals("perfumes", offerToSave.getCategory());
        assertEquals(Set.of("test"), offerToSave.getTags());
        assertEquals(LocalDate.of(2025, 3, 1), offerToSave.getValidFrom());
        assertEquals(LocalDate.of(2025, 3, 31), offerToSave.getValidTo());
    }

    @Test
    void shouldThrowExceptionWhenValidToIsBeforeValidFrom() {

        // GIVEN
        CreateOfferCommand command = new CreateOfferCommand(
                "Perfume Test",
                "Desc",
                new BigDecimal("10000"),
                "perfumes",
                Set.of("test"),
                LocalDate.of(2025, 3, 31),
                LocalDate.of(2025, 3, 1)
        );

        // WHEN + THEN
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> createOfferService.createOffer(command));

        assertEquals("validTo cannot be before validFrom", exception.getMessage());

        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    void shouldThrowExceptionWhenOfferTitleAlreadyExists() {
        // GIVEN
        CreateOfferCommand command = new CreateOfferCommand(
                "Perfume Test",
                "Desc",
                new BigDecimal("10000"),
                "perfumes",
                Set.of("test"),
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31)
        );

        when(offerRepository.existsByTitle("Perfume Test")).thenReturn(true);

        // WHEN + THEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> createOfferService.createOffer(command)
        );

        assertEquals("Offer with this title already exists", exception.getMessage());
        verify(offerRepository, never()).save(any(Offer.class));
    }
}