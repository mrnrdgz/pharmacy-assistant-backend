package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.ListOffersService;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOfferService createOfferService;

    @MockBean
    private ListOffersService listOffersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOffer() throws Exception {

        // GIVEN
        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

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

    @Test
    void shouldReturnListOfOffers() throws Exception {

        // GIVEN
        OfferResponse offer1 = new OfferResponse(
                1L,
                "Ibuprofen 400mg",
                "Pain relief tablets",
                new BigDecimal("2500.00"),
                "PAIN_RELIEF",
                Set.of("pain", "fever"),
                true,
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 31)
        );

        OfferResponse offer2 = new OfferResponse(
                2L,
                "Vitamin C",
                "Immune support supplement",
                new BigDecimal("1800.00"),
                "VITAMINS",
                Set.of("immune"),
                true,
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 30)
        );

        when(listOffersService.list()).thenReturn(List.of(offer1, offer2));

        // WHEN + THEN
        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Ibuprofen 400mg"))
                .andExpect(jsonPath("$[0].category").value("PAIN_RELIEF"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Vitamin C"))
                .andExpect(jsonPath("$[1].category").value("VITAMINS"));
    }
}