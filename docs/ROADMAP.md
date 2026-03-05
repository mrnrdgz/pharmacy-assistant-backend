# Project Roadmap

## Phase A – Foundation

- Define domain boundaries
- Set up modular monolith structure
- Configure base project
- Implement health endpoint
- Establish error handling strategy

## Phase B – Catalog Core

- Implement Offer entity
- CRUD operations for offers
- DTOs and validation
- Global exception handling
- Unit tests (service layer)
- Basic integration test

## Phase C – Knowledge & Inquiries

- FAQ read endpoints
- Inquiry registration
- Persistence and tracking

## Phase D – Containerization & CI

- Dockerfile
- docker-compose (app + postgres)
- GitHub Actions CI pipeline

## Phase E – AI Assistant

- Define Assistant orchestration service
- Context builder (offers + FAQs)
- AI client abstraction
- External AI provider integration
- WhatsApp link generation
- Inquiry persistence

---

## Non-Goals (For Now)

- Microservices
- Authentication/Authorization
- Frontend
- Caching optimization
- Advanced search engines