package com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
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
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
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
        assertEquals(LocalDate.of(2025, 3, 1), savedOffer.getValidFrom());
        assertEquals(LocalDate.of(2025, 3, 31), savedOffer.getValidTo());
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
                .validFrom(LocalDate.of(2025, 4, 1))
                .validTo(LocalDate.of(2025, 4, 30))
                .build();

        // WHEN
        Offer savedOffer = offerRepository.save(offer);

        // THEN
        assertNotNull(savedOffer.getId());
        assertTrue(savedOffer.getActive());
        assertNotNull(savedOffer.getCreatedAt());
        assertNotNull(savedOffer.getUpdatedAt());
        assertEquals(LocalDate.of(2025, 4, 1), savedOffer.getValidFrom());
        assertEquals(LocalDate.of(2025, 4, 30), savedOffer.getValidTo());
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
                .validFrom(LocalDate.of(2025, 5, 1))
                .validTo(LocalDate.of(2025, 5, 31))
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
        assertEquals(LocalDate.of(2025, 5, 1), foundOffer.getValidFrom());
        assertEquals(LocalDate.of(2025, 5, 31), foundOffer.getValidTo());
    }
}