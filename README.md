# Las Cariñosas — Investigation System

## The Setting

In the opulent city of "Las Cariñosas," a series of crimes has shaken high society. A serial killer has emerged from the shadows, targeting wealthy families and leaving a trail of death and mystery. Fear looms over the rich and powerful, who find themselves defenseless against the criminal's cunning and brutality.

## The Victims

The victims, all from wealthy lineages, share a sinister pattern: they are murdered in their opulent homes at the exact moment their seemingly perfect lives collapse. The bodies are arranged in macabre displays following a mysterious order known only to the killer. The names of the victims—the Aris, the Gonzagas, the Bonet—resonate with a somber echo through the city's history.

- **S.A:** Found in his study, her body hanging from the ceiling with golden ropes, recalling the luxury that once adorned his life.
- **MG:** Discovered in her luxurious bathroom alongside compromising photos of her lover, with her body submerged in a bathtub full of gold coins.
- **Isis the professor:** Found on a luxury yacht in "Cuba Lagoon" with Eclipse open, contemplating how to torture Data Structures students and create plain text files.

## The Investigation

The case rests on the shoulders of Detective Luis R, a man haunted by his own demons (some believe he is the killer, and he also happens to study Administrative Engineering). As he struggles to unravel the enigma, he discovers a web of secrets, lies, and greed intertwined in the heart of the city.

---

## Technical Requirements

The company **"Luna Lunera y Asociados S.A."** and its leaders, **Juan Pablo and Díaz L**, require a system with the following specifications:

1. **Framework Flexibility & Restrictions** — Any framework except Express.js (Spring Boot used here).
2. **Frontend (GUI)** — A Web-based Graphical User Interface is mandatory.
3. **Security** — Keycloak manages authentication and authorization.
4. **Containerization** — Full Docker Compose orchestration.
5. **Design Artifacts** — ERD and Class Diagram in `design/`.

---

## API Endpoints

### Cases — `/api/cases`

| Method | Endpoint | Roles Allowed | Description |
|--------|----------|---------------|-------------|
| GET | `/api/cases` | ADMIN, DETECTIVE, VIEWER | List all cases |
| GET | `/api/cases/{id}` | ADMIN, DETECTIVE, VIEWER | Get case by ID |
| POST | `/api/cases` | ADMIN, DETECTIVE | Create new case |
| PUT | `/api/cases/{id}` | ADMIN, DETECTIVE | Update case |
| DELETE | `/api/cases/{id}` | ADMIN | Delete case |

### Victims — `/api/victims`

| Method | Endpoint | Roles Allowed | Description |
|--------|----------|---------------|-------------|
| GET | `/api/victims` | ADMIN, DETECTIVE, VIEWER | List all victims |
| GET | `/api/victims/{id}` | ADMIN, DETECTIVE, VIEWER | Get victim by ID |
| POST | `/api/victims` | ADMIN, DETECTIVE | Register new victim |
| PUT | `/api/victims/{id}` | ADMIN, DETECTIVE | Update victim info |
| DELETE | `/api/victims/{id}` | ADMIN | Delete victim record |

### Evidences — `/api/evidences`

| Method | Endpoint | Roles Allowed | Description |
|--------|----------|---------------|-------------|
| GET | `/api/evidences` | ADMIN, DETECTIVE, VIEWER | List all evidences |
| GET | `/api/evidences/{id}` | ADMIN, DETECTIVE, VIEWER | Get evidence by ID |
| POST | `/api/evidences` | ADMIN, DETECTIVE | Register new evidence |
| PUT | `/api/evidences/{id}` | ADMIN, DETECTIVE | Update evidence |
| DELETE | `/api/evidences/{id}` | ADMIN | Delete evidence |

---

## Running with Docker Compose

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) installed
- [Docker Compose](https://docs.docker.com/compose/install/) installed
- A `.env` file in the project root (see below)

### 1. Create the `.env` file

Create a file named `.env` in the project root with the following content:

```properties
POSTGRES_USER=postgres
POSTGRES_PASSWORD=mysecretpassword
POSTGRES_DB=investigation_db
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=admin
KC_DB=postgres
KC_DB_URL=jdbc:postgresql://postgres:5432/investigation_db
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/investigation_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=mysecretpassword
KEYCLOAK_ISSUER_URI=http://localhost:8081/realms/spring
KEYCLOAK_JWK_URI=http://keycloak:8080/realms/spring/protocol/openid-connect/certs
```

### 2. Build and start all services

```bash
docker compose up --build -d
```

This will start three containers: `postgres_db`, `keycloak`, and `keycloak_app`.

### 3. Verify the services are running

```bash
docker compose ps
```

All three services should show status `running` or `healthy`.

### 4. Check logs

```bash
# All services
docker compose logs -f

# Only the Spring Boot app
docker compose logs -f app

# Only Keycloak
docker compose logs -f keycloak
```

### 5. Access the services

| Service | URL |
|---------|-----|
| Spring Boot API | http://localhost:8080 |
| Keycloak Admin Console | http://localhost:8081 |

### 6. Stop all services

```bash
docker compose down
```

To also remove the database volume (full reset):

```bash
docker compose down -v
```


---

## Keycloak Configuration Guide

### 1. Access the Admin Console

Once the Docker Compose stack is running, open your browser at:

```
http://localhost:8081
```

Log in with the admin credentials defined in your `.env` file (`KEYCLOAK_ADMIN` / `KEYCLOAK_ADMIN_PASSWORD`).

### 2. Create a Realm

1. Click the dropdown in the top-left corner (shows "master").
2. Click **Create Realm**.
3. Set the name to `spring` and click **Create**.

### 3. Create the Client

1. In the left menu go to **Clients** → **Create client**.
2. Fill in the fields:
   - **Client ID:** `springboot_client`
   - **Client type:** `OpenID Connect`
3. Click **Next**.
4. In the **Capability config** step, apply the following settings:
   - **Client authentication:** `Off`
   - **Authorization:** `Off`
   - **Authentication flow:** check only `Standard flow` and `Direct access grants`
5. Click **Next** → **Save**.

### 4. Create Roles

1. In the left menu go to **Clients** → `springboot_client` → **Roles** tab.
2. Click **Create role** and add the following roles one by one:

| Role | Description |
|------|-------------|
| `ADMIN` | Full access: read, write, and delete |
| `DETECTIVE` | Can read and write, but cannot delete |
| `VIEWER` | Read-only access |

### 5. Create Users and Assign Roles

1. In the left menu go to **Users** → **Create new user**.
2. Fill in the **Username** and click **Create**.
3. Go to the **Credentials** tab → **Set password** (disable "Temporary").
4. Go to the **Role mapping** tab → **Assign role**.
5. Filter by **Filter by clients**, select `springboot_client`, and assign the desired role.

### 6. Obtain an Access Token (Postman)

Make a **POST** request to:

```
http://localhost:8081/realms/spring/protocol/openid-connect/token
```

In the **Body** tab, select `x-www-form-urlencoded` and add the following fields:

| Key | Value |
|-----|-------|
| `grant_type` | `password` |
| `client_id` | `springboot_client` |
| `username` | `<your user>` |
| `password` | `<your password>` |

The response will include an `access_token`. Use it in your requests as:

```
Authorization: Bearer <access_token>
```

---


## Contact / Authors

- Project: Luna Lunera y Asociados S.A. — Investigation System (student project)
- Maintainer: repo owner