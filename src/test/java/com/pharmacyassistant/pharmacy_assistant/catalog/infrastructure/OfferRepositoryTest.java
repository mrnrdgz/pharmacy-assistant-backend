package com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfferRepositoryTest {

    @Autowired
    private IOfferRepository offerRepository;

    @Test
    void shouldSaveOffer() {

        // GIVEN
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test", "promo"))
                .build();

        // WHEN
        Offer savedOffer = offerRepository.save(offer);

        // THEN
        assertNotNull(savedOffer.getId());
        assertEquals("Perfume Test", savedOffer.getTitle());
        assertEquals("Desc", savedOffer.getDescription());
        assertEquals(0, savedOffer.getPrice().compareTo(new BigDecimal("10000")));
        assertEquals("perfumes", savedOffer.getCategory());
        assertEquals(Set.of("test", "promo"), savedOffer.getTags());
    }

    @Test
    void shouldSetDefaultValuesOnPersist() {

        // GIVEN
        Offer offer = Offer.builder()
                .title("Ibuprofen Promo")
                .description("Pain relief")
                .price(new BigDecimal("2500"))
                .category("pain_relief")
                .tags(Set.of("pain"))
                .build();

        // WHEN
        Offer savedOffer = offerRepository.save(offer);

        // THEN
        assertNotNull(savedOffer.getId());
        assertTrue(savedOffer.getActive());
        assertNotNull(savedOffer.getCreatedAt());
        assertNotNull(savedOffer.getUpdatedAt());
    }

    @Test
    void shouldFindSavedOfferById() {

        // GIVEN
        Offer offer = Offer.builder()
                .title("Vitamin C")
                .description("Immune support")
                .price(new BigDecimal("1800"))
                .category("vitamins")
                .tags(Set.of("immune"))
                .build();

        Offer savedOffer = offerRepository.save(offer);

        // WHEN
        Optional<Offer> result = offerRepository.findById(savedOffer.getId());

        // THEN
        assertTrue(result.isPresent());

        Offer foundOffer = result.get();
        assertEquals(savedOffer.getId(), foundOffer.getId());
        assertEquals("Vitamin C", foundOffer.getTitle());
        assertEquals("Immune support", foundOffer.getDescription());
        assertEquals(0, foundOffer.getPrice().compareTo(new BigDecimal("1800")));
        assertEquals("vitamins", foundOffer.getCategory());
        assertEquals(Set.of("immune"), foundOffer.getTags());
        assertTrue(foundOffer.getActive());
        assertNotNull(foundOffer.getCreatedAt());
        assertNotNull(foundOffer.getUpdatedAt());
    }
}