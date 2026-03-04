# Hotel Reservation System

Java EE web application for hotel reservation management using Servlets/JSP, DAO pattern, and MySQL.

## Features
- Login with role-based session handling
- Add, search, view, update, and delete reservations
- Bill calculation utilities
- Occupancy and revenue reporting pages
- REST endpoint for reservations (`/api/reservations/*`)
- Observer and Factory pattern examples

## Tech Stack
- Java 11
- Servlet API (Java EE 8)
- JSP
- MySQL
- Maven
- JUnit 4

## Project Structure
- `src/main/java/com/hotel/...` application source
- `src/main/webapp/...` JSP and static resources
- `database/schema.sql` database schema
- `src/test/java/...` test classes

## Setup
1. Create DB and tables using `database/schema.sql`.
2. Configure DB in `database.properties` (or use defaults in `DatabaseConnection`).
3. Build and run:
   - `mvn clean package`
4. Deploy generated WAR to a Servlet 4.0 compatible container (e.g., Tomcat 9).

## Test
- Run: `mvn test`

## Demo Credentials
- `admin / admin123`
- `manager / admin123`
- `reception / admin123`

## Role-Page Mapping
- `RECEPTION`: Dashboard, Add Reservation, View Reservations (CRUD), Calculate Bill, Help
- `MANAGER`: Dashboard, View Reservations (read-only), Reports (with CSV export), Help
- `ADMIN`: Full access to all pages and operations

## Compliance Notes
- No backend framework is used (no Spring/Quarkus/etc.).
- Backend dependencies are limited to Java EE API, DB driver, JUnit, and JSON serialization.
