# Development Workflow

This document defines how development work is organized for this project.

The goal is to maintain clarity, avoid context loss, and ensure steady progress.

---

# Daily Workflow

Each work session follows these steps:

1. Read `PROJECT_STATUS.md`
2. Identify the **current task**
3. Work only on that task
4. Implement code
5. Run tests
6. Commit changes
7. Update `PROJECT_STATUS.md` if the task was completed

---

# Work Session Duration

Recommended session length:

2–4 hours per session.

Focus on **one task at a time**.

Avoid switching tasks in the same session.

---

# Definition of Done

A task is considered complete when:

- Code compiles successfully
- Feature works as expected
- Basic test passes
- Code is committed

---

# Source of Truth

The project documentation is organized as follows:

README.md → project overview  
ARCHITECTURE.md → system architecture  
DOMAIN.md → domain model  
ROADMAP.md → development phases  
USE_CASES.md → system capabilities  
PROJECT_STATUS.md → current progress

---

# Development Principle

No implementation should start unless the task exists in:

- ROADMAP.md
- USE_CASES.md
- PROJECT_STATUS.md