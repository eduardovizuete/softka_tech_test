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

### La solución se debe desplegar y funcionar en Docker
Compile microservices

	microservice micro_person_client  
 
		mvn clean package -DskipTests  
  
	microservice micro_account_tx  
 
		mvn clean package  

Docker Container docker-compose.yml (service = micropc, service 2 = microat and database = mysqldb)

	docker compose up 
 
	docker exec -it micropc bin/sh  
 
	docker exec -it microat bin/sh  
 
	docker exec -it mysqldb bash  
 
		mysql -u root -p  
  
		show databases;  
  
		use microdev 
  
		show tables;  
  
		select * from person; 
  
		select * from client; 
  
		select * from account;  
  
		select * from transaction;  
  
	docker compose down  

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
	
    /micro_account_tx/src/main/resources/BaseDatos.sql.sql  
 
	/micro_person_client/src/main/resources/BaseDatos.sql.sql

### Proyecto postman para probar REST API

    /softka_tech_test/microdev/sofka_micro.postman_test_api.collection.json