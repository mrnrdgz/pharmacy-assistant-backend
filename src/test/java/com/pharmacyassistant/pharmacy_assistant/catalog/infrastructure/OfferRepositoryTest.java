package com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure;

import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OfferRepositoryTest {

    @Autowired
    private IOfferRepository offerRepository;

    @Test
    @DisplayName("Should save and retrieve an Offer from database")
    void shouldSaveAndFindOffer() {

        // GIVEN
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Fragancia test")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .build();

        // WHEN
        Offer savedOffer = offerRepository.save(offer);

        Optional<Offer> foundOffer = offerRepository.findById(savedOffer.getId());

        // THEN
        assertThat(savedOffer.getId()).isNotNull();

        assertThat(foundOffer).isPresent();
        assertThat(foundOffer.get().getTitle()).isEqualTo("Perfume Test");
        assertThat(foundOffer.get().getPrice()).isEqualByComparingTo("10000");
        assertThat(foundOffer.get().getTags()).contains("test");
    }
}