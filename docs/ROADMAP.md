# Project Roadmap

This roadmap defines the **planned development phases** of the project.
Each phase focuses on a specific capability of the system.

---

## Phase A — Foundation

- Define domain boundaries
- Establish modular monolith structure
- Configure base Spring Boot project
- Implement health endpoint
- Define global error handling strategy
- Write initial architecture and domain documentation

---

## Phase B — Catalog Module

Goal: implement the **offer management core**.

- Implement Offer entity
- Create Offer use case
- List Offers endpoint
- Update Offer
- Disable Offer
- DTOs and validation
- Service layer unit tests
- Basic integration test

---

## Phase C — Knowledge Module

Goal: provide assistant knowledge context.

- Implement FAQ entity
- List FAQs endpoint
- FAQ persistence
- Basic tests

---

## Phase D — Inquiries Module

Goal: track user questions and assistant responses.

- Implement Inquiry entity
- Inquiry registration endpoint
- Persistence and tracking
- Basic tests

---

## Phase E — Assistant Module

Goal: implement AI-assisted recommendation flow.

- Assistant orchestration service
- Context builder (offers + FAQs)
- AI client abstraction
- External AI provider integration
- Response generation
- Inquiry persistence

---

## Phase F — Delivery & DevOps

- Dockerfile
- docker-compose (app + postgres)
- CI pipeline (GitHub Actions)
- Basic observability setup

---

## Non-Goals (For Now)

These topics are intentionally **out of scope** for the current version:

- Microservices architecture
- Authentication / Authorization
- Frontend implementation
- Advanced caching strategies
- Search engine integration