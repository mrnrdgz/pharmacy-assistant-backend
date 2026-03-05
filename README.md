# Pharmacy Assistant Backend

A modular monolith backend built with Spring Boot 3 and Java 17.

This project is designed as:

- Technical interview preparation (Senior Software Engineer level)
- Real-world applicable backend for a pharmacy business
- Foundation for AI-assisted gift recommendation features
- Production-ready structure with Docker and CI/CD

---

## Tech Stack

- Java 17
- Spring Boot 3
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

Controller → Service → Repository → Database

---

## Current Status

Phase A – Project Bootstrap & Architecture Definition

See `/docs/ROADMAP.md` for detailed plan.