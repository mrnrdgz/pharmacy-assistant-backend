# Project Status

## Current Phase
Phase B — Catalog Module

## Current Module
Catalog

## Current Task
Catalog module completed

## Completed

- Project bootstrap with Spring Boot
- Maven configuration
- Base package structure
- Health endpoint
- Initial documentation
- Test configuration

- Offer entity implemented
- Database tables created (offers, offer_tags)

- Create Offer use case implemented
- CreateOfferService
- OfferController
- Create Offer - Controller test
- Create Offer - Service test
- Create Offer - Repository test
- Create Offer - Integration test (valid + validation errors)

- List Offers use case implemented
- ListOffersService
- GET /offers endpoint
- OfferResponse DTO
- List Offers - Service test
- List Offers - Controller test

- Update Offer use case implemented
- UpdateOfferService
- PUT /offers/{id}
- Update Offer - Service test
- Update Offer - Controller test

- Disable Offer use case implemented
- DisableOfferService
- PATCH /offers/{id}/disable
- Disable Offer - Service test
- Disable Offer - Controller test

- Global exception handling implemented
- Validation error responses standardized

- Business rules implemented:
  - validTo cannot be before validFrom
  - offer title must be unique

- Unit tests for business rules
- Integration tests:
  - validation errors
  - invalid dates
  - duplicated title

---

## In Progress

- None

---

## Next Tasks

1. Start Knowledge module (FAQ)
2. Implement FAQ entity
3. Create FAQ use case
4. List FAQs endpoint