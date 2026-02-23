# Las Cariñosas — Investigation System

## The Setting

In the opulent city of "Las Cariñosas," a series of crimes has shaken high society. A serial killer has emerged from the shadows, targeting wealthy families and leaving a trail of death and mystery. Fear looms over the rich and powerful, who find themselves defenseless against the criminal's cunning and brutality.

## The Victims

The victims, all from wealthy lineages, share a sinister pattern: they are murdered in their opulent homes at the exact moment their seemingly perfect lives collapse. The bodies are arranged in macabre displays following a mysterious order known only to the killer. The names of the victims—the Aris, the Gonzagas, the Bonet—resonate with a somber echo through the city's history.

- S.A: Found in his study, her body hanging from the ceiling with golden ropes, recalling the luxury that once adorned his life.
- MG: Discovered in her luxurious bathroom alongside compromising photos of her lover, with her body submerged in a bathtub full of gold coins.
- Isis the professor: Found on a luxury yacht in "Cuba Lagoon" with Eclipse open, contemplating how to torture Data Structures students and create plain text files.

## The Investigation

The case rests on the shoulders of Detective Luis R, a man haunted by his own demons (some believe he is the killer, and he also happens to study Administrative Engineering). As he struggles to unravel the enigma, he discovers a web of secrets, lies, and greed intertwined in the heart of the city.

## Assignment & Technical Requirements

The company "Luna Lunera y Asociados S.A." and its leaders, Juan Pablo and Díaz L, require a system with the following specifications:

1. Framework Flexibility & Restrictions
   - You may use any framework of your choice (e.g., NestJS, Spring Boot, FastAPI, Go Fiber).
   - The use of Express.js is strictly forbidden.

2. Frontend (GUI)
   - A Web-based Graphical User Interface is mandatory.
   - Any frontend framework may be used (React, Angular, Vue, or Vanilla JS).

3. Security
   - Implement Keycloak to manage authentication and authorization to protect sensitive investigation data.

4. Containerization
   - Both the application and the database must be Dockerized.
   - Provide a `docker-compose.yml` to orchestrate the environment.

5. Design Artifacts
   - Deliver the Entity-Relationship Diagram (ERD) and the Class Diagram of the application.


## Project Status (current repository)

This repository currently contains a Spring Boot backend with two main domains: `cases` and `victims`.

- Endpoints implemented (controllers):
  - `GET /api/cases` — list cases
  - `GET /api/cases/{id}` — get case by id
  - `POST /api/cases` — create case
  - `PUT /api/cases/{id}` — update case
  - `DELETE /api/cases/{id}` — delete case

  - `GET /api/victims` — list victims
  - `GET /api/victims/{id}` — get victim by id
  - `POST /api/victims` — create victim
  - `PUT /api/victims/{id}` — update victim
  - `DELETE /api/victims/{id}` — delete victim

- DTOs: `CaseDto`, `VictimDto` (see `src/main/java/com/impl/keycloak/*`)
- Note: Spring Security / Keycloak dependencies were previously present in `pom.xml` but were removed temporarily to ease local testing. Security must be (re)enabled to satisfy the assignment.
- There is a `docker-compose.yml` in the repo root; review and adapt it to include Keycloak and the database as required.


## How to run (local development)

1. Build and run with Maven:

```bash
# from project root
./mvnw clean package
java -jar target/keycloak-0.0.1-SNAPSHOT.jar
```

or during development:

```bash
./mvnw spring-boot:run
```

2. Open the API

- Base URL: `http://localhost:8080`
- Example endpoint: `GET http://localhost:8080/api/cases`

3. Docker (recommended for full stack with DB and Keycloak)

- Ensure `docker` and `docker-compose` are installed.
- Update or create `docker-compose.yml` to include:
  - The Spring Boot application service
  - Postgres database service
  - Keycloak service (or use a dedicated Keycloak container)

Example (high level):

```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - 8080:8080
    depends_on:
      - db
      - keycloak
  db:
    image: postgres:14
    environment:
      POSTGRES_DB: investigation_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
  keycloak:
    image: quay.io/keycloak/keycloak:21.1.1
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      # configure realm, admin user, etc.
    ports:
      - 8081:8080
```

Adjust as needed and ensure the application `application.yml` references the Keycloak issuer or JWK URI for resource-server validation.


## Security: re-enabling Keycloak (high-level steps)

1. Add back the following dependencies to `pom.xml`:
   - `spring-boot-starter-security`
   - `spring-boot-starter-security-oauth2-resource-server`

2. Configure `application.yml` with Keycloak issuer or `jwk-set-uri`:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://<KEYCLOAK_HOST>/realms/<REALM>
```

3. Optionally add a `SecurityConfig` to tune which endpoints are public and which require authentication.

4. Start Keycloak, create a realm, clients/users, then obtain an access token and call the API with `Authorization: Bearer <token>`.


## Design Artifacts

Place ERD and Class Diagram files in a directory `design/` at the repo root. Preferred formats: PNG, SVG, or the native diagram format (e.g. draw.io `.drawio`). Example files to add:

- `design/ERD.png` — Entity-Relationship Diagram
- `design/ClassDiagram.png` — Class Diagram

If you want, I can create initial diagram files from the current entities and classes and add them to the repo.


## Deliverables

- Working backend with Cases & Victims API (this repo)
- Keycloak configuration (to be added or enabled)
- Docker Compose for app + DB + Keycloak (update `docker-compose.yml`)
- Frontend GUI (not included yet) — create a `frontend/` directory and add code
- Design artifacts in `design/`


## Next steps / Recommendations

- Re-enable Keycloak and secure endpoints per requirement #3.
- Dockerize Keycloak and the database, update `docker-compose.yml`, and verify all services start and the app can validate tokens.
- Create a minimal frontend that lists cases and victims and supports CRUD operations. Protect edit/create routes behind Keycloak authentication.
- Add ERD and Class Diagram in `design/`.
- Create a Postman collection for API testing (I can generate and add it).


## Contact / Authors
- Project: Luna Lunera y Asociados S.A. — Investigation System (student project)
- Maintainer: repo owner


---
*This README was generated to document the assignment, current repo status, and next steps to finish the task.*
