# softka_tech_test

## Indicaciones generales

- Aplique todas las buenas prácticas, patrones Repository, etc que considere necesario
(se tomará en cuenta este punto para la calificación).

- El manejo de entidades se debe manejar JPA / Entity Framework Core

- Se debe manejar mensajes de excepciones.

- Se debe realizar como mínimo dos pruebas unitarias de los endpoints.

- La solución se debe desplegar en Docker.

## Herramientas y tecnologías utilizadas

- Java spring boot

- IDE de su preferencia

- Base de Datos Relacional

- Postman v9.13.2 (validador de API) / Karate DSL
  
## Características implementadas

- F1: Generación de CRUDS (Crear, editar, actualizar y eliminar registros - Entidades: Cliente,
  Cuenta y Movimiento).
- F2: Registro de movimientos
- F3: Registro de movimientos - errores
- Reportes: Generar un reporte de “Estado de cuenta” especificando un rango de fechas y
  cliente
- F5: Pruebas unitarias: Implementar 1 prueba unitaria para la entidad de dominio Cliente

    /micro_person_client/src/test/java/com/job/micro/personclient/entity/ClientTest.java

- F6: Pruebas de Integración: Implementar 1 prueba de integración

  /micro_person_client/src/test/java/com/job/micro/personclient/controller/ClientControllerTest.java

- Senior: 

    Implementar en 2 microservicios, agrupando (Cliente, Persona) y (Cuenta, Movimientos) donde se contemple una comunicación asincrónica entre los 2 microservicios. 

    Cumplir las funcionalidades F1, F2, F3, F4, F5, F6, F7 

    La solución debe contemplar (no necesariamente implementado) factores como: rendimiento, escalabilidad, resiliencia.

## Características adicionales

- Microservicio Service & Discovery
- Microservicio Api Gateway (WebFlux)
- Microservicio Security (JWT + JWK + JWKS OAuth Nimbus JOSE JWT implementation RSA)
- Docker compose (mysqldb, zookeeper, kafka, service-registry, api-gateway, security, micropc, microat)

### Ejecución local sin docker

Requerimientos:

- Ejecutar zookeper, kafka y bd local (mysql)

  ``` shell
  cd opt/kafka
  bin/zookeeper-server-start.sh config/zookeeper.properties
  bin/kafka-server-start.sh config/server.properties
  ```

### La solución se debe desplegar y funcionar en Docker

Compile microservices

    microservice service-registry
    mvn clean package -DskipTests

    microservice api-gateway
    mvn clean package -DskipTests

    microservice security
    mvn clean package -DskipTests

	microservice micro_person_client
    mvn clean package -DskipTests  
  
	microservice micro_account_tx
    mvn clean package -DskipTests 

Docker Container docker-compose.yml (mysqldb, zookeeper, kafka, service-registry, api-gateway, security, micropc, microat)

	docker compose up 
 
	docker exec -it micropc bin/sh  
 
	docker exec -it microat bin/sh  
 
	docker exec -it mysqldb bash  
 
		mysql -u root -p
		show databases;
		use microdev;
		show tables;
		select * from person;
		select * from client;
		select * from account;
		select * from transaction;
        exit
  
    docker exec -it service-registry bin/sh

    docker exec -it api-gateway bin/sh

    docker exec -it security bin/sh
  
	docker compose down  
    
    view logs

        docker compose logs -f mysqldb
        docker compose logs -f micropc
        docker compose logs -f microat

    docker compose down --rmi all

### Separar en 2 microservicios, agrupando (Cliente, Persona) y (Cuenta, Movimientos) donde se contemple una comunicación asincrónica entre los 2 microservicios

    /micro_account_tx/src/main/java/com/job/micro/accounttx/controller/AccountController.java

        Método -> createAccountAsync

### Generación de CRUDS (Crear, editar, actualizar y eliminar registros - Entidades: Cliente,  Cuenta y Movimiento). 

    Los nombres de los endpoints a generar son:
    
    Cliente -> /api/clients
    
    Cuenta -> /api/accounts
    
    Movimiento -> /api/transactions
    
    Reporte estado de cuenta /api/reports
    
    REST API reports

	    http://localhost:8081/api/reports?clientId=1&startDate=2024-10-01T09:41:00Z&endDate=2024-12-01T09:42:00Z

### script database schema and tables
	
    /softka_tech_test/microdev/init.sql

### Proyecto postman para probar REST API

    /softka_tech_test/microdev/sofka_micro.postman_test_api.collection.json

### Command line test API

  token authentication

  ```shell
  curl -X POST "http://localhost:9191/api/auth/login" \                                           
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
  ```

  client list
  ```shell
  curl -i -X GET "http://localhost:9191/api/clients" \
     -H "Authorization: Bearer <token>"
  ```

  create client
  ```shell
  curl -X POST "http://localhost:9191/api/accounts" \
     -H "Authorization: Bearer <toke>" \
     -H "Content-Type: application/json" \
     -d '{
           "number": "478759",
           "type": "Ahorros",
           "balance": "2000.00",
           "status": "Activo",
           "clientId" : 111
         }'
  ```

# Microservices Architecture Documentation

## 1. Service Registry (`service-registry`)
- **Purpose**: Discovery service for microservices
- **Technology**: Spring Cloud Eureka
- **Container**: Dockerized with Alpine Java 22
- **Timezone**: America/Guayaquil
- **Role**: Central registry for all microservices

## 2. API Gateway (`api-gateway`)
- **Purpose**: Entry point for all client requests
- **Key Components**:
  - JWT Token Filter
  - Request Routing
  - Security Configuration
  - Circuit Breaker Configuration
  - Load Balancing
- **Security**: Implements JWT token authentication and authorization

## 3. Account Transaction Service (`micro_account_tx`)
- **Purpose**: Manages account transactions and operations
- **Key Components**:
  - `AccountController`: REST endpoints for account operations
  - `AccountServiceImpl`: Business logic for accounts
  - `TransactionService`: Transaction management
  - `TransactionStrategy`: Transaction execution patterns
- **Features**:
  - Account creation and management
  - Transaction processing
  - Asynchronous operations
  - Circuit breaker for resilience
  - Integration with client service

## 4. Person Client Service (`micro_person_client`)
- **Purpose**: Manages client/person information
- **Key Components**:
  - `ClientController`: REST endpoints for client operations
  - `ClientServiceImpl`: Business logic for client management
  - `PersonRepository`: Data access layer
  - `ClientValidator`: Input validation
- **Features**:
  - Client registration
  - Client information management
  - Integration with account service
  - Data validation and sanitization

## 5. Security Layer
- **Authentication**: OAuth2 authorization server
- **Token Management**: JWT token authentication
- **Security Components**:
  - Custom JWT token filter
  - JWK set configuration
  - Role-based access control
- **Integration**: Secure communication between microservices

## 6. Infrastructure
- **Containerization**: Docker-based deployment
- **Framework**: Spring Boot microservices
- **Service Discovery**: Spring Cloud
- **Security**: Spring Security
- **Utilities**:
  - ModelMapper for DTO conversion
  - WebClient for service-to-service communication

## Architecture Patterns
- **Design**: Clean Architecture principles
- **Service Pattern**: Microservices architecture with service registry
- **Resilience**: Circuit breaker pattern
- **Communication**: Asynchronous processing capabilities
- **API Design**: RESTful API design
- **Security**: JWT-based security implementation

## Service Integration
- **API Gateway** → Routes requests to appropriate services
- **Account Service** ↔ **Client Service** → Synchronous and asynchronous communication
- **All Services** ↔ **Service Registry** → Service discovery and registration
- **Security Layer** → Protects all microservice communications

## Technology Stack
- **Core Framework**: Spring Boot
- **Cloud Services**: Spring Cloud
- **Security**: Spring Security, OAuth2, JWT
- **Container**: Docker
- **Database**: (Not specified in current context)
- **Development Tools**: Java, Maven/Gradle

## TODO
- Implement rotation, refresh, revoke token, improve performance using Redis
- Replace security implementation with current framework spring security, replace JWK deprecated
- Implement Auth2 with Keycloak
- Implement centralized transaction logging, trace and audit
- Monitoring with ELK stack, signoz, datadog, splunk
- Monitoring with actuator, prometheus and grafana
- Update spring boot and spring cloud version
- Transform to hexagonal clean architecture