# Railway Reservation System

Full-stack microservices project built with:
- **Backend**: Java 17, Spring Boot 3, Spring Cloud
- **Frontend**: React 18 + Vite
- **Database**: PostgreSQL (H2 for dev)
- **Messaging**: Apache Kafka
- **Infrastructure**: Docker, Docker Compose
- **Observability**: Prometheus + Grafana

## Services
| Service | Port | Description |
|---|---|---|
| discovery-server | 8761 | Eureka service registry |
| api-gateway | 8080 | Spring Cloud Gateway |
| auth-service | 9898 | JWT authentication |
| train-service | 8082 | Train and route management |
| booking-service | 8083 | Booking and PNR |
| railway-ui | 5173 | React frontend |

## Getting Started
```bash
# Start in this order
1. discovery-server
2. api-gateway
3. auth-service, train-service, booking-service (any order)
4. railway-ui
```

## Architecture
> Architecture diagram coming soon