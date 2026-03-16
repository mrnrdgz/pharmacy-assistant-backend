# Architecture Overview

## Architectural Style

The system is designed as a **Modular Monolith**.

### Why?

- Lower operational complexity
- Faster development iteration
- Transactional consistency
- Simpler deployment
- Suitable for small-to-medium domain complexity

The codebase is organized into modules with clear internal boundaries,
allowing future extraction into microservices if necessary.

---

## Layered Architecture

Each module follows the same layered structure:

web → application → domain → infrastructure

### Layer Responsibilities

**web**

- REST controllers
- HTTP request/response handling
- DTO mapping

**application**

- Use cases
- Application services
- Coordination of domain operations

**domain**

- Entities
- Value objects
- Core business rules

**infrastructure**

- Database access
- JPA repositories
- External integrations

---

## Dependency Rules

Dependencies must follow this direction:

web → application → domain  
infrastructure → domain

The **domain layer must not depend on Spring or infrastructure frameworks.**

---

## Core Modules

### catalog
Manages pharmacy offers available for recommendation.

### knowledge
Stores FAQ and pharmacy-related knowledge.

### inquiries
Tracks customer questions and assistant responses.

### assistant
Coordinates AI interaction and builds assistant responses.

---

## AI Integration (Future)

AI will be integrated through an abstraction layer:

AssistantService → AiClient (interface) → Provider Implementation

Examples of implementations:

- OpenAI client
- Mock client (for tests)
- Local model client

Benefits:

- vendor independence
- easier testing
- flexible provider switching