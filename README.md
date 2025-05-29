# microservices-projekt
Microservices API Gateway System
This system consists of four microservices with an API Gateway as the central entry point:

1.API Gateway-Handles incoming traffic and authentication

2.Auth Server-Manages user authentication and JWT tokens

3.Joke Service-Returns random jokes (requires authentication)

4.Quote Service - Returns random quotes (requires authentication)

Prerequisites

Java 17

Maven

Docker

Kubernetes (Docker Desktop recommended)

kubectl

Running the System
1. Spring Boot (Local Development)

   bash
# In each service directory:
mvn spring-boot:run

# Default ports:
# -API Gateway: 8081
# -Auth Server: 8080
# -Joke Service: 8082
# -Quote Service: 8083

2. Docker Compose
   bash
   docker-compose up --build

# Test:
curl -X POST http://localhost:8081/api/auth/login -H "Content-Type: application/json" -d '{"username":"user","password":"password"}'
3. Kubernetes (Docker Desktop)

   bash
# Enable Kubernetes in Docker Desktop
kubectl apply -f k8s/

# Wait for all pods to be ready:
kubectl get pods -w

# Test:
curl -X POST http://localhost/api/auth/login -H "Content-Type: application/json" -d '{"username":"user","password":"password"}'
Testing the System
Get token:

bash
TOKEN=$(curl -s -X POST http://localhost/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"user","password":"password"}' | jq -r '.token')
Get random joke:

bash
curl -H "Authorization: Bearer $TOKEN" http://localhost/api/jokes/random
Get random quote:

bash
curl -H "Authorization: Bearer $TOKEN" http://localhost/api/quotes/random


Architecture Diagram
[Client]
│
▼
[Ingress (Nginx)]
│
▼
[API Gateway]
├──► [Auth Server]
├──► [Joke Service]
└──► [Quote Service]


Configuration
JWT secret key: your-secret-key-that-is-long-and-secure (change in production)

Spring profiles: default, docker, kubernetes


Improvement Potential: Token Management
Current Solution
Symmetric JWT with a secret key (HMAC-SHA256)

Same secret shared between services

Key stored in environment variables

Problems
Security risk: If one service is compromised, attackers can generate valid tokens

Key rotation: Difficult to rotate keys without downtime

Limited control: Same key used for all services


Proposed Improvement: Asymmetric JWT with JWKS
Diagram
Code
Implementation Steps


Generate key pair:

bash
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -out public.pem

JWKS Endpoint:

java
@RestController
public class JwksController {
@GetMapping("/.well-known/jwks.json")
public Map<String, Object> getJwks() {
// Return JWKS with public key
}
}


Token validation in API Gateway:

java
@Bean
public JwtDecoder jwtDecoder() {
return NimbusJwtDecoder.withJwkSetUri("http://auth-server/.well-known/jwks.json")
.build();
}


Benefits
Enhanced security: Private key only exists on Auth Server

Easier key rotation: Add new keys to JWKS without downtime

Multiple key support: Ability to use kid (Key ID)

Standardized: Open standard (JWKS)



Kubernetes Implementation
yaml
# Auth Server Deployment
env:
- name: JWT_PRIVATE_KEY
  valueFrom:
  secretKeyRef:
  name: jwt-secrets
  key: private-key

# API Gateway Deployment
env:
- name: JWKS_URI
  value: "http://auth-server-service/.well-known/jwks.json"
  Summary
  The proposed improvement implements an industry standard (JWKS) for better security and scalability. By using asymmetric cryptography and centralized key management, we reduce the risk of token compromises and make the system more flexible for future development.