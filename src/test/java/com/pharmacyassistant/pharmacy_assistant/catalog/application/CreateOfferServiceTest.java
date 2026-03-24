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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
                Set.of("test")
        );

        Offer savedOffer = Offer.builder()
                .id(1L)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .build();

        when(offerRepository.save(any(Offer.class))).thenReturn(savedOffer);

        // WHEN
        Offer result = createOfferService.createOffer(command);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Perfume Test", result.getTitle());

        ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
        verify(offerRepository).save(captor.capture());

        Offer offerToSave = captor.getValue();
        assertEquals("Perfume Test", offerToSave.getTitle());
        assertEquals("Desc", offerToSave.getDescription());
        assertEquals(new BigDecimal("10000"), offerToSave.getPrice());
        assertEquals("perfumes", offerToSave.getCategory());
        assertEquals(Set.of("test"), offerToSave.getTags());
    }
}