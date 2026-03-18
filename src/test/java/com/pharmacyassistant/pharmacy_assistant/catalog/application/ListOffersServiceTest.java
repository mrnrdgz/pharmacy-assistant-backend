package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.OfferResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListOffersServiceTest {

    @Mock
    private IOfferRepository offerRepository;

    @InjectMocks
    private ListOffersService listOffersService;

    @Test
    void shouldReturnListOfOfferResponses() {
        // given
        Offer offer1 = Offer.builder()
                .id(1L)
                .title("Ibuprofen 400mg")
                .description("Pain relief tablets")
                .price(new BigDecimal("2500.00"))
                .category("PAIN_RELIEF")
                .tags(Set.of("pain", "fever"))
                .active(true)
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
                .build();

        Offer offer2 = Offer.builder()
                .id(2L)
                .title("Vitamin C")
                .description("Immune support supplement")
                .price(new BigDecimal("1800.00"))
                .category("VITAMINS")
                .tags(Set.of("immune", "daily"))
                .active(true)
                .validFrom(LocalDate.of(2025, 4, 1))
                .validTo(LocalDate.of(2025, 4, 30))
                .build();

        when(offerRepository.findAll()).thenReturn(List.of(offer1, offer2));

        // when
        List<OfferResponse> result = listOffersService.list();

        // then
        assertEquals(2, result.size());

        OfferResponse first = result.get(0);
        assertEquals(1L, first.getId());
        assertEquals("Ibuprofen 400mg", first.getTitle());
        assertEquals("Pain relief tablets", first.getDescription());
        assertEquals(new BigDecimal("2500.00"), first.getPrice());
        assertEquals("PAIN_RELIEF", first.getCategory());
        assertEquals(Set.of("pain", "fever"), first.getTags());
        assertEquals(true, first.getActive());
        assertEquals(LocalDate.of(2025, 3, 1), first.getValidFrom());
        assertEquals(LocalDate.of(2025, 3, 31), first.getValidTo());
    }
}