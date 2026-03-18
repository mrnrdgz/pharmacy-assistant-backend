package com.pharmacyassistant.pharmacy_assistant.catalog.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OfferTest {

    @Test
    void shouldSetDefaultValuesOnCreate() {
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .build();

        assertNull(offer.getCreatedAt());
        assertNull(offer.getUpdatedAt());
        assertNull(offer.getActive());

        offer.onCreate();

        assertNotNull(offer.getCreatedAt());
        assertNotNull(offer.getUpdatedAt());
        assertTrue(offer.getActive());
    }

    @Test
    void shouldNotOverrideActiveIfAlreadyDefined() {
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(false)
                .build();

        offer.onCreate();

        assertFalse(offer.getActive());
    }

    @Test
    void shouldUpdateUpdatedAtOnUpdate() {
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(true)
                .build();

        offer.onCreate();
        LocalDateTime firstUpdatedAt = offer.getUpdatedAt();

        offer.onUpdate();

        assertNotNull(offer.getUpdatedAt());
        assertFalse(offer.getUpdatedAt().isBefore(firstUpdatedAt));
    }
}