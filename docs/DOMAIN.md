# Domain Model (Draft)

## Core Concepts

### Offer
A recommendation-ready catalog item intended for promotions and gifting suggestions.
Not a full inventory product.

Key fields: title, description, price, category, tags, active, validity window.

### FAQ
Curated knowledge used to ground assistant responses.

Key fields: question, answer, category, active.

### Inquiry
A persisted record of a user interaction and the system output.

Key fields: channel, userMessage, detectedPreferences, recommendedOfferIds, assistantResponse, createdAt.

### AssistantContext (non-persistent)
A composed view used by the assistant to produce an answer deterministically.

Contains: preferences, relevantOffers, relevantFaqs, constraints.