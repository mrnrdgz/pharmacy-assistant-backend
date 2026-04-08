package com.pharmacyassistant.pharmacy_assistant.catalog.integration;

import com.pharmacyassistant.pharmacy_assistant.catalog.infrastructure.IOfferRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OfferIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IOfferRepository offerRepository;

    @Test
    @DisplayName("Should create offer when request is valid")
    void shouldCreateOfferWhenRequestIsValid() throws Exception {
        String requestBody = """
            {
              "title": "Perfume Test",
              "description": "Discount on perfumes",
              "price": 10000,
              "category": "perfumes",
              "tags": ["fragance", "luxury"],
              "validFrom": "2025-03-01",
              "validTo": "2025-03-31"
            }
            """;

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Perfume Test"))
                .andExpect(jsonPath("$.description").value("Discount on perfumes"))
                .andExpect(jsonPath("$.category").value("perfumes"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.validFrom").value("2025-03-01"))
                .andExpect(jsonPath("$.validTo").value("2025-03-31"));

        assertThat(offerRepository.findAll()).hasSize(1);

        var savedOffer = offerRepository.findAll().get(0);
        assertThat(savedOffer.getTitle()).isEqualTo("Perfume Test");
        assertThat(savedOffer.getDescription()).isEqualTo("Discount on perfumes");
        assertThat(savedOffer.getCategory()).isEqualTo("perfumes");
        assertThat(savedOffer.getPrice()).isEqualByComparingTo("10000");
        assertThat(savedOffer.getActive()).isTrue();
    }

    @Test
    @DisplayName("Should return bad request when title is blank")
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        String requestBody = """
            {
              "title": "",
              "description": "Discount on perfumes",
              "price": 10000,
              "category": "perfumes",
              "tags": ["fragance", "luxury"],
              "validFrom": "2025-03-01",
              "validTo": "2025-03-31"
            }
            """;

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("Title is required"));

        assertThat(offerRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Should return bad request when price is negative")
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {
        String requestBody = """
            {
              "title": "Perfume Test",
              "description": "Discount on perfumes",
              "price": -10,
              "category": "perfumes",
              "tags": ["fragance", "luxury"],
              "validFrom": "2025-03-01",
              "validTo": "2025-03-31"
            }
            """;

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0].field").value("price"))
                .andExpect(jsonPath("$.errors[0].message").value("Price must be greater than zero"));

        assertThat(offerRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Should return validTo cannot be before validFrom")
    void shouldReturnBadRequestWhenValidToIsBeforeValidFrom()  throws Exception {
        String requestBody = """
            {
              "title": "Perfume Test",
              "description": "Discount on perfumes",
              "price": 10,
              "category": "perfumes",
              "tags": ["fragance", "luxury"],
              "validFrom": "2025-03-31",
              "validTo": "2025-03-01"
            }
            """;

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("validTo cannot be before validFrom"));

        assertThat(offerRepository.findAll()).isEmpty();
    }
    @Test
    @DisplayName("Should return bad request when offer title already exists")
    void shouldReturnBadRequestWhenOfferTitleAlreadyExists() throws Exception {

        String requestBody = """
        {
          "title": "Perfume Test",
          "description": "Discount on perfumes",
          "price": 10000,
          "category": "perfumes",
          "tags": ["fragance", "luxury"],
          "validFrom": "2025-03-01",
          "validTo": "2025-03-31"
        }
        """;

        // 1. Crear la primera oferta
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        // 2. Intentar crear otra con el mismo title
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Offer with this title already exists"));

        // 3. Verificar que solo hay una en DB
        assertThat(offerRepository.findAll()).hasSize(1);
    }
}