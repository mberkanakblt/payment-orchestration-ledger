# Payment Orchestration & Ledger Platform

> 🚧 **Bu proje aktif olarak geliştirilmektedir.**

Modern bir ödeme işleme sistemi. Hexagonal Architecture (Ports & Adapters) prensiplerine uygun olarak tasarlanmıştır.

## 🏗️ Mimari

```
┌─────────────────────────────────────────────────────────────┐
│                        API Layer                            │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Account    │  │  Transfer   │  │  Payment    │         │
│  │  Controller │  │  Controller │  │  Controller │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                    Application Layer                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Account    │  │  Transfer   │  │  Payment    │         │
│  │  Service    │  │  Service    │  │  Service    │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Hold       │  │  Ledger     │  │ Idempotency │         │
│  │  Service    │  │  Service    │  │  Service    │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                      Domain Layer                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Account    │  │  Payment    │  │  Transfer   │         │
│  │  (Aggregate)│  │  (Aggregate)│  │  (Entity)   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │  Money      │  │ PaymentStat │  │  Hold       │         │
│  │(ValueObject)│  │(ValueObject)│  │  (Entity)   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│                  Infrastructure Layer                       │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              Persistence Adapters                    │   │
│  │  (JPA Entities, Repositories, Mappers)              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 Özellikler

### ✅ Tamamlanan
- [x] Hesap Yönetimi (Account)
- [x] Para Transferi (Transfer)
- [x] Idempotency Desteği
- [x] Payment Lifecycle (Authorize → Capture → Refund / Void)
- [x] Hold Sistemi (Para Blokesi)
- [x] Double-Entry Ledger (Çift Kayıt Muhasebe)
- [x] Hexagonal Architecture

### 🔄 Geliştirme Aşamasında
- [ ] Global Exception Handling
- [ ] Unit & Integration Tests
- [ ] Merchant Yönetimi
- [ ] Customer Yönetimi

## 💳 Payment Lifecycle

```
AUTHORIZE ──────► CAPTURE ──────► REFUND
    │                               
    └──────────► VOID              
```

| Durum | Açıklama |
|-------|----------|
| `AUTHORIZED` | Para hold edildi, capture bekleniyor |
| `CAPTURED` | Para müşteriden çekildi |
| `VOID` | Auth iptal edildi (capture öncesi) |
| `REFUNDED` | Para iade edildi |
| `DECLINED` | Yetersiz bakiye - reddedildi |

## 📚 API Endpoints

### Account
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/accounts` | Hesap oluştur |
| GET | `/accounts/{id}` | Hesap detayı |

### Transfer
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/transfers` | Para transferi |
| GET | `/transfers/{id}` | Transfer detayı |

### Payment
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/payments/authorize` | Ödeme authorize et |
| POST | `/payments/{id}/capture` | Ödemeyi capture et |
| POST | `/payments/{id}/void` | Ödemeyi iptal et |
| POST | `/payments/{id}/refund` | Ödemeyi iade et |
| GET | `/payments/{id}` | Ödeme detayı |

## 🔐 Idempotency

Tüm POST istekleri `Idempotency-Key` header'ı gerektirir:

```http
POST /transfers
Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json

{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100,
  "currency": "TRY"
}
```

Aynı key ile tekrar istek gönderildiğinde:
- İşlem tekrar yapılmaz
- Önceki sonuç döndürülür

## 📊 Double-Entry Ledger

Her finansal işlem çift kayıt ile tutulur:

**Capture:**
```
DEBIT  Customer Account  -500 TL  (pairId: 123)
CREDIT Merchant Account  +500 TL  (pairId: 123)
```

**Refund:**
```
DEBIT  Merchant Account  -500 TL  (pairId: 456)
CREDIT Customer Account  +500 TL  (pairId: 456)
```

## 🛠️ Teknolojiler

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Flyway** (Database Migration)
- **Lombok**
- **Springdoc OpenAPI** (Swagger)
- **Testcontainers**

## 🏃 Çalıştırma

### Gereksinimler
- Java 21+
- PostgreSQL
- Maven

### Adımlar

1. PostgreSQL veritabanı oluştur:
```sql
CREATE DATABASE paycoredb;
```

2. `application.yml` dosyasını düzenle:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/paycoredb
    username: postgres
    password: root
```

3. Uygulamayı çalıştır:
```bash
mvn spring-boot:run
```

4. Swagger UI'a eriş:
```
http://localhost:9090/swagger-ui/index.html
```

## 📁 Proje Yapısı

```
src/main/java/com/mehmetberkan/paycore/
├── api/
│   ├── controller/          # REST Controllers
│   ├── dto/                  # Request/Response DTOs
│   └── mapper/               # REST Mappers
├── application/
│   ├── port/
│   │   ├── in/              # Input Ports (Use Cases)
│   │   └── out/             # Output Ports
│   └── service/             # Application Services
├── domain/
│   ├── aggregate/           # Aggregates (Account, Payment)
│   ├── model/               # Entities (Transfer, Hold, Ledger)
│   ├── valueobject/         # Value Objects (Money, PaymentStatus)
│   ├── enums/               # Enums
│   └── exception/           # Domain Exceptions
└── infrastructure/
    ├── persistence/         # JPA Entities, Repos, Adapters
    └── repository/          # Repositories
```

## 👨‍💻 Geliştirici

**Mehmet Berkan**

---

⭐ Bu proje aktif olarak geliştirilmektedir.

