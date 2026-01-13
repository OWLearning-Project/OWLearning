# OWLearning Copilot Instructions

## Architecture Overview
This project follows Clean Architecture principles for an online learning platform (OWLearning):
- **Domain**: Core business logic and entities in `Domain/Models/` (e.g., `Utilisateur.java`, `Discussion.java`)
- **Application**: Use cases and services in `Application/Services/`, mappers in `Application/Mapper/`
- **Infrastructure**: External implementations in `Infrastructure/Persistence/` (Entity, Repository) and `Infrastructure/Config/`
- **Api**: REST endpoints organized by feature: `Api/Auth/`, `Api/Cours/`, `Api/Messagerie/`, `Api/Progression/`
- **Shared**: Common DTOs in `Shared/DTO/`, enums in `Shared/Enums/`, exceptions in `Shared/Exceptions/`

Data flows from Api → Application → Domain, with Infrastructure providing implementations via dependency injection.

## Build and Run
- Build: `mvn clean compile`
- Test: `mvn test`
- Run: `mvn spring-boot:run`
- Full build: `mvn clean install`

Uses Spring Boot 4.0.1 with Java 25.

## Code Patterns
- Domain models use SQL types (e.g., `Timestamp` for dates) and French field names (e.g., `prenom`, `nom`)
- Flat package structure under `src/main/java/` (no deep nesting like `com.example.*`)
- Entities have simple constructors and getters/setters; validate setters for correct assignments (e.g., in `Utilisateur.java`, setters may have bugs like `this.nom = nom` instead of `this.nom = unNom`)

## Key Files
- `Domain/Models/Utilisateur.java`: User entity with registration and activity timestamps
- `Domain/Models/Discussion.java`: Discussion entity with participants and messages
- `pom.xml`: Spring Boot dependencies and Java 25 configuration

## Conventions
- Use Maven for dependency management
- Place architecture diagrams in `docs/` (PDFs for class, sequence, and architecture diagrams)
- Interfaces in `Domain/Ports/IRepository/` and `Domain/Ports/IServices/` define contracts for infrastructure and application layers