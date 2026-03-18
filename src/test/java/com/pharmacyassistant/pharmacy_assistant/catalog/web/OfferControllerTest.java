package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOfferService createOfferService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOffer() throws Exception {

        // GIVEN (dato de entrada)
        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        // AND (respuesta simulada del service)
        Offer offer = Offer.builder()
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .build();

        when(createOfferService.createOffer(request)).thenReturn(offer);

        // WHEN + THEN
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}