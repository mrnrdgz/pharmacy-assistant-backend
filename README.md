# Pharmacy Assistant Backend

A modular monolith backend built with Spring Boot 3 and Java 17.

This project is designed as:

- Technical interview preparation (Senior Software Engineer level)
- Real-world applicable backend for a pharmacy business
- Foundation for AI-assisted gift recommendation features
- Production-ready structure with Docker and CI/CD

---

## Tech Stack

- Java 21
- Spring Boot 3.5.x
- Maven
- PostgreSQL
- JUnit + Mockito
- Docker
- GitHub Actions (CI)

---

## Architecture Overview

The application follows a **modular monolith architecture** with clear boundaries:

- catalog → Offer management
- knowledge → FAQ management
- inquiries → User inquiries tracking
- assistant → AI orchestration layer

Each module follows layered architecture:

web → application → domain → infrastructure

---

## Current Status

Phase A — Foundation

Completed:

- Spring Boot project bootstrap
- Maven configuration
- Base package structure
- Health endpoint

Next step:

Catalog module — Create Offer use case

See `/docs/ROADMAP.md` for detailed plan.