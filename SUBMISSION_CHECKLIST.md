# Submission Checklist (Assignment-Aligned)

## 1) Backend Rules
- [ ] Java EE web app architecture is used (Servlet/JSP + WAR).
- [ ] No backend framework is used (`Spring`, `Spring Boot`, `Spring MVC`, `Quarkus`, etc.).
- [ ] `pom.xml` includes only allowed backend dependency categories:
  - [ ] Java EE dependency
  - [ ] Database dependency
  - [ ] JUnit dependency
  - [ ] Optional reporting/serialization dependency only if needed

## 2) Functional Evidence (Screenshots + Brief Notes)
- [ ] Login page and successful login
- [ ] Dashboard metrics
- [ ] Add reservation flow
- [ ] View/search reservation flow
- [ ] Update/delete reservation flow
- [ ] Bill generation flow
- [ ] Report page + CSV export flow
- [ ] REST endpoint test (`/api/reservations/*`)
- [ ] Role restrictions (`RECEPTION`, `MANAGER`, `ADMIN`)

## 3) Report Quality
- [ ] All diagrams are clear and readable
- [ ] All screenshots are clear and readable
- [ ] All charts/graphs/stats are clear and readable
- [ ] No low-resolution or blurred images
- [ ] Captions/labels are present where needed

## 4) Submission Restrictions
- [ ] No Google Drive / OneDrive / third-party storage links included as evidence
- [ ] Report content aligns to marking criteria sections
- [ ] GitHub repository is Public

## 5) Pre-Submission Technical Check
- [ ] Database schema imported successfully (`database/schema.sql`)
- [ ] Application builds (`mvn clean package`)
- [ ] WAR deploys and runs in Tomcat
- [ ] Critical pages and API are re-tested after final build
