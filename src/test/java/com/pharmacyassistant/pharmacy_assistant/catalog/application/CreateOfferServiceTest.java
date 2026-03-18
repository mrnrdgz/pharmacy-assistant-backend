package com.pharmacyassistant.pharmacy_assistant.catalog.application;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.CreateOfferRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOfferServiceTest {

    @Mock
    private IOfferRepository offerRepository;

    @InjectMocks
    private CreateOfferService createOfferService;

    @Test
    void shouldCreateOffer() {
        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        Offer savedOffer = Offer.builder()
                .id(1L)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .build();

        when(offerRepository.save(any(Offer.class))).thenReturn(savedOffer);

        Offer result = createOfferService.createOffer(request);

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