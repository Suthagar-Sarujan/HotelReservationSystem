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
- `manager / manager123`
- `reception / reception123`

## Role-Page Mapping
- `RECEPTION`: Dashboard, Add Reservation, View Reservations (CRUD), Calculate Bill, Help
- `MANAGER`: Dashboard, View Reservations (read-only), Reports (with CSV export), Help
- `ADMIN`: Full access to all pages and operations

## Compliance Notes
- No backend framework is used (no Spring/Quarkus/etc.).
- Backend dependencies are limited to Java EE API, DB driver, JUnit, and JSON serialization.

## Assignment Compliance (Strict)
### Backend Framework Policy
- This project uses Java EE Servlets/JSP only.
- No Spring Boot, Spring MVC, Quarkus, or similar backend frameworks are used.

### Allowed Backend Dependencies Used
- Java EE: `javax:javaee-web-api`
- Database: `mysql:mysql-connector-java`
- Testing: `junit:junit`
- Serialization (optional): `com.google.code.gson:gson`

### Submission Quality Rules
- All diagrams, screenshots, graphs, and stats in the report must be clear and readable.
- Do not include Google Drive / OneDrive / third-party storage URLs as submission evidence.
- Report content must align directly with the official marking criteria.
- GitHub repository must remain Public.

## Final Submission Checklist
- [ ] Build succeeds with `mvn clean package`
- [ ] WAR deploys to a Servlet 4.0 compatible server (Tomcat 9 recommended)
- [ ] Database schema from `database/schema.sql` is applied successfully
- [ ] Login and role-based access flows are demonstrable
- [ ] Reservation CRUD, billing, and reporting pages are demonstrable
- [ ] REST endpoint `/api/reservations/*` is demonstrable
- [ ] Report has readable diagrams/screenshots/stats/graphs
- [ ] Report contains no third-party storage links
- [ ] Report content maps to assignment marking criteria
- [ ] GitHub repository visibility is Public
