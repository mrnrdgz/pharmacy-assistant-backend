package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.*;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private UpdateOfferService updateOfferService;

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
                .id(1L)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(true)
                .build();

        when(createOfferService.createOffer(any(CreateOfferCommand.class))).thenReturn(offer);

        // WHEN + THEN
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Perfume Test"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.price").value(10000))
                .andExpect(jsonPath("$.category").value("perfumes"))
                .andExpect(jsonPath("$.active").value(true));

        verify(createOfferService, times(1)).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnBadRequestWhenPriceIsNegative() throws Exception {

        // GIVEN
        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("-100"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        // WHEN + THEN
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(createOfferService, times(0)).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {

        // GIVEN
        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        // WHEN + THEN
        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(createOfferService, times(0)).createOffer(any(CreateOfferCommand.class));
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
                .andExpect(jsonPath("$[0].description").value("Pain relief tablets"))
                .andExpect(jsonPath("$[0].price").value(2500.00))
                .andExpect(jsonPath("$[0].category").value("PAIN_RELIEF"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Vitamin C"))
                .andExpect(jsonPath("$[1].description").value("Immune support supplement"))
                .andExpect(jsonPath("$[1].price").value(1800.00))
                .andExpect(jsonPath("$[1].category").value("VITAMINS"))
                .andExpect(jsonPath("$[1].active").value(true));

        verify(listOffersService, times(1)).list();
    }
    @Test
    void shouldReturnBadRequestWhenCategoryIsBlank() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory(""); // inválido
        request.setTags(Set.of("test"));

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(createOfferService, times(0)).createOffer(any());
    }
    @Test
    void shouldUpdateOffer() throws Exception {

        // GIVEN
        Long offerId = 1L;

        UpdateOfferRequest request = new UpdateOfferRequest();
        request.setTitle("Updated Perfume");
        request.setDescription("Updated Desc");
        request.setPrice(new BigDecimal("15000"));
        request.setCategory("updated-category");
        request.setTags(Set.of("updated", "promo"));

        Offer updatedOffer = Offer.builder()
                .id(offerId)
                .title("Updated Perfume")
                .description("Updated Desc")
                .price(new BigDecimal("15000"))
                .category("updated-category")
                .tags(Set.of("updated", "promo"))
                .active(true)
                .build();

        when(updateOfferService.updateOffer(any(Long.class), any(UpdateOfferCommand.class)))
                .thenReturn(updatedOffer);

        // WHEN + THEN
        mockMvc.perform(put("/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Perfume"))
                .andExpect(jsonPath("$.description").value("Updated Desc"))
                .andExpect(jsonPath("$.price").value(15000))
                .andExpect(jsonPath("$.category").value("updated-category"))
                .andExpect(jsonPath("$.active").value(true));

        verify(updateOfferService, times(1))
                .updateOffer(any(Long.class), any(UpdateOfferCommand.class));
    }

    @Test
    void shouldReturnBadRequestWhenUpdatingOfferWithNegativePrice() throws Exception {

        // GIVEN
        Long offerId = 1L;

        UpdateOfferRequest request = new UpdateOfferRequest();
        request.setTitle("Updated Perfume");
        request.setDescription("Updated Desc");
        request.setPrice(new BigDecimal("-15000"));
        request.setCategory("updated-category");
        request.setTags(Set.of("updated", "promo"));

        // WHEN + THEN
        mockMvc.perform(put("/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(updateOfferService, never())
                .updateOffer(any(Long.class), any(UpdateOfferCommand.class));
    }

    @Test
    void shouldReturnBadRequestWhenUpdatingOfferWithBlankTitle() throws Exception {

        // GIVEN
        Long offerId = 1L;

        UpdateOfferRequest request = new UpdateOfferRequest();
        request.setTitle("");
        request.setDescription("Updated Desc");
        request.setPrice(new BigDecimal("15000"));
        request.setCategory("updated-category");
        request.setTags(Set.of("updated", "promo"));

        // WHEN + THEN
        mockMvc.perform(put("/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(updateOfferService, never())
                .updateOffer(any(Long.class), any(UpdateOfferCommand.class));
    }
}