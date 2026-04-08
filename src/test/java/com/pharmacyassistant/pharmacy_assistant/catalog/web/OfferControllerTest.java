package com.pharmacyassistant.pharmacy_assistant.catalog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.DisableOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.ListOffersService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.UpdateOfferService;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.CreateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.dto.UpdateOfferCommand;
import com.pharmacyassistant.pharmacy_assistant.catalog.domain.Offer;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.OfferController;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.CreateOfferRequest;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.OfferResponse;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.dto.UpdateOfferRequest;
import com.pharmacyassistant.pharmacy_assistant.catalog.web.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
@Import(GlobalExceptionHandler.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOfferService createOfferService;

    @MockBean
    private ListOffersService listOffersService;

    @MockBean
    private UpdateOfferService updateOfferService;

    @MockBean
    private DisableOfferService disableOfferService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOffer() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));
        request.setValidFrom(LocalDate.of(2025, 3, 1));
        request.setValidTo(LocalDate.of(2025, 3, 31));

        Offer offer = Offer.builder()
                .id(1L)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(true)
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
                .build();

        when(createOfferService.createOffer(any(CreateOfferCommand.class))).thenReturn(offer);

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Perfume Test"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.price").value(10000))
                .andExpect(jsonPath("$.category").value("perfumes"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.validFrom").value("2025-03-01"))
                .andExpect(jsonPath("$.validTo").value("2025-03-31"));

        verify(createOfferService, times(1)).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnValidationErrorWhenPriceIsNegative() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("-100"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[*].field", hasItem("price")));

        verify(createOfferService, never()).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnValidationErrorWhenTitleIsBlank() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[*].field", hasItem("title")));

        verify(createOfferService, never()).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnValidationErrorWhenCategoryIsBlank() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("");
        request.setTags(Set.of("test"));

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[*].field", hasItem("category")));

        verify(createOfferService, never()).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnBusinessErrorWhenValidToIsBeforeValidFromOnCreate() throws Exception {

        CreateOfferRequest request = new CreateOfferRequest();
        request.setTitle("Perfume Test");
        request.setDescription("Desc");
        request.setPrice(new BigDecimal("10000"));
        request.setCategory("perfumes");
        request.setTags(Set.of("test"));
        request.setValidFrom(LocalDate.of(2025, 3, 31));
        request.setValidTo(LocalDate.of(2025, 3, 1));

        when(createOfferService.createOffer(any(CreateOfferCommand.class)))
                .thenThrow(new RuntimeException("validTo cannot be before validFrom"));

        mockMvc.perform(post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("validTo cannot be before validFrom"))
                .andExpect(jsonPath("$.errors").isArray());

        verify(createOfferService, times(1)).createOffer(any(CreateOfferCommand.class));
    }

    @Test
    void shouldReturnListOfOffers() throws Exception {

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

        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Ibuprofen 400mg"))
                .andExpect(jsonPath("$[0].validFrom").value("2025-03-01"))
                .andExpect(jsonPath("$[0].validTo").value("2025-03-31"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Vitamin C"))
                .andExpect(jsonPath("$[1].validFrom").value("2025-04-01"))
                .andExpect(jsonPath("$[1].validTo").value("2025-04-30"));

        verify(listOffersService, times(1)).list();
    }

    @Test
    void shouldUpdateOffer() throws Exception {

        Long offerId = 1L;

        UpdateOfferRequest request = new UpdateOfferRequest();
        request.setTitle("Updated Perfume");
        request.setDescription("Updated Desc");
        request.setPrice(new BigDecimal("15000"));
        request.setCategory("updated-category");
        request.setTags(Set.of("updated", "promo"));
        request.setValidFrom(LocalDate.of(2025, 4, 1));
        request.setValidTo(LocalDate.of(2025, 4, 30));

        Offer updatedOffer = Offer.builder()
                .id(offerId)
                .title("Updated Perfume")
                .description("Updated Desc")
                .price(new BigDecimal("15000"))
                .category("updated-category")
                .tags(Set.of("updated", "promo"))
                .active(true)
                .validFrom(LocalDate.of(2025, 4, 1))
                .validTo(LocalDate.of(2025, 4, 30))
                .build();

        when(updateOfferService.updateOffer(any(Long.class), any(UpdateOfferCommand.class)))
                .thenReturn(updatedOffer);

        mockMvc.perform(put("/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Perfume"))
                .andExpect(jsonPath("$.validFrom").value("2025-04-01"))
                .andExpect(jsonPath("$.validTo").value("2025-04-30"));

        verify(updateOfferService, times(1))
                .updateOffer(any(Long.class), any(UpdateOfferCommand.class));
    }

    @Test
    void shouldReturnBusinessErrorWhenValidToIsBeforeValidFromOnUpdate() throws Exception {

        Long offerId = 1L;

        UpdateOfferRequest request = new UpdateOfferRequest();
        request.setTitle("Updated Perfume");
        request.setDescription("Updated Desc");
        request.setPrice(new BigDecimal("15000"));
        request.setCategory("updated-category");
        request.setTags(Set.of("updated", "promo"));
        request.setValidFrom(LocalDate.of(2025, 4, 30));
        request.setValidTo(LocalDate.of(2025, 4, 1));

        when(updateOfferService.updateOffer(any(Long.class), any(UpdateOfferCommand.class)))
                .thenThrow(new RuntimeException("validTo cannot be before validFrom"));

        mockMvc.perform(put("/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("validTo cannot be before validFrom"))
                .andExpect(jsonPath("$.errors").isArray());

        verify(updateOfferService, times(1))
                .updateOffer(any(Long.class), any(UpdateOfferCommand.class));
    }

    @Test
    void shouldDisableOffer() throws Exception {

        Long offerId = 1L;

        Offer disabledOffer = Offer.builder()
                .id(offerId)
                .title("Perfume Test")
                .description("Desc")
                .price(new BigDecimal("10000"))
                .category("perfumes")
                .tags(Set.of("test"))
                .active(false)
                .validFrom(LocalDate.of(2025, 3, 1))
                .validTo(LocalDate.of(2025, 3, 31))
                .build();

        when(disableOfferService.disableOffer(offerId)).thenReturn(disabledOffer);

        mockMvc.perform(patch("/offers/{id}/disable", offerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false))
                .andExpect(jsonPath("$.validFrom").value("2025-03-01"));

        verify(disableOfferService, times(1)).disableOffer(offerId);
    }
}