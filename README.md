# Car Store Microservices

A microservices-based platform for managing car listings, built with Spring Boot, Apache Kafka, and PostgreSQL. The system is composed of three independent services that communicate via REST and asynchronous Kafka events.

---

## Architecture Overview

```
                        ┌─────────────────────┐
                        │    API Service       │
         Client ───────▶│    (Port 8085)       │
                        │  REST + Kafka Prod.  │
                        └──────────┬──────────┘
                                   │
                    ┌──────────────▼──────────────┐
                    │        car-post-topic         │
                    │        (Apache Kafka)         │
                    └──────────────┬──────────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              ▼                                         ▼
  ┌─────────────────────┐               ┌─────────────────────┐
  │  Car Store Service  │               │  Analytics Service   │
  │    (Port 8086)      │               │    (Port 8087)       │
  │  PostgreSQL         │               │  PostgreSQL          │
  │  (car_store)        │               │  (car_post_analytics)│
  └─────────────────────┘               └─────────────────────┘
```

**Communication patterns:**
- **Synchronous (REST):** API Service calls Car Store Service for CRUD operations
- **Asynchronous (Kafka):** API Service publishes car posts; both Car Store and Analytics services consume them independently

---

## Services

### 1. API Service — `com.portal.api`
**Port:** `8085`

The entry point for all client interactions. Exposes REST endpoints, forwards requests to the Car Store Service via `RestClient`, and publishes car post events to Kafka.

#### Endpoints

**Car Posts** — `/api/car`

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/car/post` | Publish a new car listing (sends event to Kafka) |
| GET | `/api/car/posts` | Retrieve all car listings |
| PUT | `/api/car/{id}` | Update a car listing |
| DELETE | `/api/car/{id}` | Delete a car listing |

**Owners** — `/owner`

| Method | Path | Description |
|--------|------|-------------|
| POST | `/owner` | Create a new owner |

#### Request Body — `CarPostDTO`

```json
{
  "model": "string",
  "brand": "string",
  "price": "number",
  "description": "string",
  "engineVersion": "string",
  "city": "string",
  "createdDate": "date",
  "ownerId": "number",
  "ownerName": "string",
  "ownerType": "string",
  "contact": "string"
}
```

#### Request Body — `OwnerPostDTO`

```json
{
  "name": "string",
  "type": "string",
  "contactNumber": "string"
}
```

---

### 2. Car Store Service — `com.store.car`
**Port:** `8086` | **Database:** `car_store` (PostgreSQL)

Manages car inventory and owner data. Persists records to PostgreSQL and consumes `CarPostDTO` events from Kafka to save new listings.

#### Endpoints

**Sales** — `/sales`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/sales/cars` | Get all car listings |
| PUT | `/sales/car/{id}` | Update a car listing |
| DELETE | `/sales/car/{id}` | Delete a car listing |

**Users** — `/user`

| Method | Path | Description |
|--------|------|-------------|
| POST | `/user` | Create a new owner |

#### Database Schema

**`car_post`**

| Column | Type | Description |
|--------|------|-------------|
| id | Long (PK) | Auto-generated ID |
| model | String | Car model |
| brand | String | Car brand |
| price | Number | Listing price |
| description | String | Description |
| engineVersion | String | Engine version |
| city | String | Location |
| createdDate | Date | Post date |
| contact | String | Contact info |
| owner_id | Long (FK) | Reference to owner |

**`owner_post`**

| Column | Type | Description |
|--------|------|-------------|
| id | Long (PK) | Auto-generated ID |
| name | String | Owner name |
| type | String | Owner type |
| contactNumber | String | Phone number |

---

### 3. Analytics Service — `com.analytics.data`
**Port:** `8087` | **Database:** `car_post_analytics` (PostgreSQL)

Consumes car post events from Kafka and builds aggregated analytics across three tables: brand statistics, model statistics, and historical price records.

#### Database Schema

**`brand_analytics`**

| Column | Type | Description |
|--------|------|-------------|
| id | Long (PK) | Auto-generated ID |
| brand | String | Car brand |
| posts | Integer | Total listings count |

**`car_model_analytics`**

| Column | Type | Description |
|--------|------|-------------|
| id | Long (PK) | Auto-generated ID |
| model | String | Car model |
| posts | Integer | Total listings count |

**`car_model_price`**

| Column | Type | Description |
|--------|------|-------------|
| id | Long (PK) | Auto-generated ID |
| model | String | Car model |
| price | Number | Listing price |

> The Analytics Service has no REST endpoints — it operates purely as an event consumer.

---

## Kafka Event Flow

**Topic:** `car-post-topic`

| Role | Service | Consumer Group |
|------|---------|---------------|
| Producer | API Service | — |
| Consumer | Car Store Service | `store-posts-group` |
| Consumer | Analytics Service | `analytics-posts-group` |

When a `POST /api/car/post` request is received:
1. The API Service publishes a `CarPostDTO` to `car-post-topic`
2. The Car Store Service saves the new listing to PostgreSQL
3. The Analytics Service updates brand, model, and price analytics tables

---

## Technology Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Language |
| Spring Boot 4.x | Framework |
| Spring Data JPA | ORM / data access |
| Spring Web MVC | REST APIs |
| Spring Kafka | Kafka producer & consumer |
| Apache Kafka | Async event streaming |
| PostgreSQL | Relational database |
| Lombok | Boilerplate reduction |
| Jackson | JSON serialization |

---

## Prerequisites

- Java 17+
- Apache Kafka running on `localhost:19092`
- PostgreSQL running on `localhost:5432`
- Two databases created:
  - `car_store`
  - `car_post_analytics`

---

## Running the Services

Start each service independently from its directory:

```bash
# API Service
cd api && ./mvnw spring-boot:run

# Car Store Service
cd car && ./mvnw spring-boot:run

# Analytics Service
cd data && ./mvnw spring-boot:run
```

---

## Configuration

Each service is configured via `application.properties`.

**API Service** (`api/src/main/resources/application.properties`)
```properties
server.port=8085
spring.kafka.bootstrap-servers=localhost:19092
```

**Car Store Service** (`car/src/main/resources/application.properties`)
```properties
server.port=8086
spring.datasource.url=jdbc:postgresql://localhost:5432/car_store
spring.datasource.username=<your-username>
spring.kafka.bootstrap-servers=localhost:19092
spring.jpa.hibernate.ddl-auto=update
```

**Analytics Service** (`data/src/main/resources/application.properties`)
```properties
server.port=8087
spring.datasource.url=jdbc:postgresql://localhost:5432/car_post_analytics
spring.datasource.username=<your-username>
spring.kafka.bootstrap-servers=localhost:19092
spring.jpa.hibernate.ddl-auto=update
```

---

## Project Structure

```
car-store-microservices/
├── api/                        # API Gateway Service
│   └── src/main/java/com/portal/api/
│       ├── controller/         # CarPostController, OwnerPostController
│       ├── dto/                # CarPostDTO, OwnerPostDTO
│       ├── service/            # CarPostStoreService, OwnerPostService
│       ├── kafka/              # KafkaProducerMessage, KafkaProducerConfiguration
│       └── client/             # CarPostStoreClient, RestClientConfig
│
├── car/                        # Car Store Service
│   └── src/main/java/com/store/car/
│       ├── controller/         # CarPostController, OwnerPostController
│       ├── dto/                # CarPostDTO, OwnerPostDTO
│       ├── service/            # CarPostService, OwnerPostService
│       ├── entity/             # CarPostEntity, OwnerPostEntity
│       ├── repository/         # CarPostRepository, OwnerPostRepository
│       └── kafka/              # KafkaConsumerMessage, KafkaConsumerConfigs
│
└── data/                       # Analytics Service
    └── src/main/java/com/analytics/data/
        ├── service/            # PostAnalyticsService
        ├── entity/             # BrandAnalyticsEntity, CarModelAnalyticsEntity, CarModelPriceEntity
        ├── repository/         # BrandAnalyticsRepository, CarModelAnalyticsRepository, CarPriceAnalyticsRepository
        └── kafka/              # KafkaConsumerMessage, KafkaConsumerConfigs
```
