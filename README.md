# EventAllInOneApp - Spring Boot Backend

This is a ready-to-import Spring Boot backend for the EventAllInOneApp.

How to run:
1. Update `src/main/resources/application.properties` with your MySQL and SMTP settings.
2. Build and run:
   - `mvn clean package`
   - `mvn spring-boot:run`
3. Swagger UI: `http://localhost:8080/swagger-ui.html`

Notes:
- No authentication is included (open APIs).
- Email sending uses Spring Mail; configure SMTP credentials before testing booking emails.
