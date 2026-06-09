# NexShop

[![Stable Tag](https://img.shields.io/badge/stable--tag-stable--coldboot--v1-blue.svg)](#)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Microservices-brightgreen.svg)](#)
[![React](https://img.shields.io/badge/React-Vite-blue.svg)](#)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-Enabled-blue.svg)](#)

## 1. Project Overview
**NexShop** is a scalable, enterprise-grade Java Full Stack E-commerce platform built using a robust Microservices architecture. It demonstrates modern engineering practices including distributed tracing, centralized configuration, service discovery, asynchronous messaging, and containerized deployment using Docker and Kubernetes.

## 2. Features
- **User Authentication & Profiles**: Secure user registration, login, and profile management.
- **Product Catalog Management**: Comprehensive product listings, search, and categorization using both relational and NoSQL databases.
- **Shopping Cart**: Real-time shopping cart operations and session management.
- **Order Processing**: Seamless checkout, order placement, and asynchronous order fulfillment.
- **AI Integration**: Intelligent features integrated directly into the shopping experience.
- **Microservices Resilience**: API Gateway routing, centralized config, and service discovery.
- **Observability**: Distributed tracing with Zipkin and messaging with RabbitMQ.

## 3. Architecture Summary
NexShop is structured around decoupled **Spring Boot Microservices**. The **React (Vite)** frontend communicates with the backend exclusively through the **Spring Cloud Gateway**, which is exposed via an **NGINX Ingress** controller in Kubernetes. 
Backend services register with the **Eureka Server** for dynamic service discovery and retrieve their runtime properties from the **Config Server**. Inter-service communication is handled synchronously via REST/OpenFeign and asynchronously using **RabbitMQ**. **Zipkin** is utilized for end-to-end distributed tracing. Data is stored across **MySQL** and **MongoDB Atlas** depending on the specific microservice's domain constraints.

## 4. Tech Stack

| Domain | Technologies Used |
| :--- | :--- |
| **Frontend** | React, Vite |
| **Backend Framework** | Java 21, Spring Boot, Spring Cloud |
| **Service Discovery** | Netflix Eureka |
| **API Gateway** | Spring Cloud Gateway |
| **Configuration** | Spring Cloud Config |
| **Message Broker** | RabbitMQ |
| **Distributed Tracing** | Zipkin |
| **Databases** | MySQL, MongoDB Atlas |
| **Infrastructure / DevOps** | Docker Desktop Kubernetes, NGINX Ingress |

## 5. Microservices Description

| Service Name | Description | Port (Local) |
| :--- | :--- | :--- |
| `config-server` | Centralized configuration management for all microservices. | `8888` |
| `eureka-server` | Service Registry and Discovery server. | `8761` |
| `api-gateway` | Edge server routing frontend traffic to backend services. | `8000` |
| `user-service` | Manages user data, authentication, and security. | `8081` |
| `product-service` | Manages the product catalog, leveraging MySQL & MongoDB. | `8082` |
| `cart-service` | Handles shopping cart state and temporary sessions. | `8083` |
| `order-service` | Processes orders, payments, and fulfillment workflows. | `8084` |
| `ai-service` | Provides AI-driven features (e.g., recommendations). | `8085` |
| `frontend` | The React SPA serving the user interface. | `80 / 5173` |

## 6. Folder Structure
```text
RevShop-Microservices/
├── AI-service/           # AI capabilities microservice
├── api-gateway/          # Spring Cloud Gateway
├── cart-service/         # Shopping Cart microservice
├── config-server/        # Spring Cloud Config Server
├── eureka-server/        # Netflix Eureka Service Registry
├── frontend/             # React + Vite application
├── k8s/                  # Kubernetes Deployment Manifests
├── order-service/        # Order management microservice
├── product-service/      # Product catalog microservice
└── user-service/         # User management microservice
```

## 7. Prerequisites
Before running the project, ensure you have the following installed:
- **Java 21**
- **Maven** (3.8+)
- **Node.js & npm** (v18+)
- **Docker & Docker Desktop**
- **Kubernetes** (Enabled via Docker Desktop)
- **NGINX Ingress Controller** (Installed on local K8s cluster)
- **MongoDB Atlas** account (or local MongoDB)
- **MySQL Server** (if running entirely locally without containers)

## 8. Local Setup Instructions
If you wish to run the services locally (outside of Kubernetes):

1. **Start Infrastructure**: Run local instances of RabbitMQ, Zipkin, and MySQL (preferably via Docker).
2. **Start Config Server**: Navigate to `config-server` and run `mvn spring-boot:run`.
3. **Start Eureka Server**: Navigate to `eureka-server` and run `mvn spring-boot:run`.
4. **Start Microservices**: Start `api-gateway`, `user-service`, `product-service`, `cart-service`, `order-service`, and `ai-service` using Maven.
5. **Start Frontend**: 
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## 9. Kubernetes Deployment Instructions
The project is configured for deployment on local Kubernetes (Docker Desktop).

1. Ensure the NGINX Ingress controller is installed on your cluster.
2. Update your local `/etc/hosts` (or `C:\Windows\System32\drivers\etc\hosts`) to point `localhost` or your custom domain to the ingress controller IP.
3. Deploy the resources in the correct order:
   ```bash
   # 1. Apply secrets and configurations
   kubectl apply -f k8s/secrets.yaml

   # 2. Deploy Databases & Infrastructure
   kubectl apply -f k8s/mysql/
   kubectl apply -f k8s/rabbitmq/
   kubectl apply -f k8s/zipkin/

   # 3. Deploy Discovery & Config (Wait for them to be ready)
   kubectl apply -f k8s/configserver/
   kubectl apply -f k8s/eureka/

   # 4. Deploy Microservices
   kubectl apply -f k8s/user-service/
   kubectl apply -f k8s/product-service/
   kubectl apply -f k8s/cart-service/
   kubectl apply -f k8s/order-service/
   kubectl apply -f k8s/ai-service/
   kubectl apply -f k8s/api-gateway/

   # 5. Deploy Frontend & Ingress
   kubectl apply -f k8s/frontend/
   kubectl apply -f k8s/ingress.yaml
   ```

## 10. Access URLs
Once deployed via Kubernetes and Ingress, access the services using:

- **Frontend Application**: [http://localhost/](http://localhost/)
- **API Endpoints (Gateway)**: [http://localhost/api/*](http://localhost/api)
- **Eureka Dashboard**: [http://localhost:8761](http://localhost:8761) *(Port-forwarded)*
- **Zipkin Dashboard**: [http://localhost:9411](http://localhost:9411) *(Port-forwarded)*
- **RabbitMQ Management**: [http://localhost:15672](http://localhost:15672) *(Port-forwarded)*

## 11. API Documentation Section
API documentation is generated using `springdoc-openapi`.
- **Unified Swagger UI**: Accessible via the API Gateway at [http://localhost/swagger-ui](http://localhost/swagger-ui) (or [http://localhost/v3/api-docs](http://localhost/v3/api-docs)).
- Individual microservices expose their OpenAPI definitions which are aggregated by the Gateway.

## 12. Screenshots Section
*(Placeholders - Add actual images to a `docs/images/` folder and update paths)*

![Home Page](/docs/images/placeholder-home.png)
*Figure 1: NexShop Home Page*

![Shopping Cart](/docs/images/placeholder-cart.png)
*Figure 2: Shopping Cart and Checkout Flow*

![Eureka Dashboard](/docs/images/placeholder-eureka.png)
*Figure 3: Eureka Service Discovery Dashboard*

![Zipkin Tracing](/docs/images/placeholder-zipkin.png)
*Figure 4: Zipkin Distributed Tracing*

## 13. Future Enhancements
- **Security Upgrade**: Implement Centralized OAuth2/OIDC authentication using Keycloak.
- **Caching Layer**: Integrate Redis to cache product catalog requests and cart sessions.
- **CI/CD Pipelines**: Add GitHub Actions or Jenkins pipelines for automated testing and Docker image builds.
- **Cloud Migration**: Deploy the Kubernetes manifests to a managed cluster (AWS EKS or GCP GKE).

## 14. Contributors Section
- **Lead Architect / Developer**: [Your Name/Handle]

*Contributions are welcome! Please fork the repository and submit a pull request with your changes.*