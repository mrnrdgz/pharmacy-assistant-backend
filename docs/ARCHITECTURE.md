# Architecture Overview

## Architectural Style

The system is designed as a **Modular Monolith**.

### Why?

- Lower operational complexity
- Faster development iteration
- Transactional consistency
- Simpler deployment
- Suitable for small-to-medium domain complexity

The codebase is structured by modules with clear internal boundaries to allow future extraction if necessary.

---

## Layered Architecture

Each module follows this structure:

- API Layer (Controllers, DTOs)
- Service Layer (Business logic / Use cases)
- Domain Layer (Entities and business rules)
- Persistence Layer (Repositories)

Dependency direction:

API → Service → Domain → Persistence

The Domain layer must not depend on Spring-specific APIs.

---

## Core Modules

### catalog
Manages offer items available for recommendation.

### knowledge
Stores static FAQ knowledge for assistant context.

### inquiries
Tracks user interactions and generated responses.

### assistant
Orchestrates AI interactions, context building, and recommendation logic.

---

## Future AI Integration

AI will be integrated via an abstraction layer:

AssistantService → AiClient (interface) → Implementation (OpenAI or other provider)

This ensures:
- Testability
- Vendor independence
- Cost control during development