# Demo Employee API

Simple Spring Boot REST API for managing employees.
No authentication is configured in this demo.

You can run it locally with 
```bash
./mvnw spring-boot:run
```
or
```bash
mvn spring-boot:run
```

- **Base URL:** `http://localhost:8080`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

## Employee model

`Employee`
- `id` (Long, read-only)
- `firstName` (String)
- `lastName` (String)
- `emailAdress` (Unique string)

## Endpoints

- Visit swaggerdocs at `http://localhost:8080/swagger-ui/index.html`

### Get employee by ID
`GET /api/{id}`

**Responses**
- `200 OK`, Employee found
- `404 Not Found`, No employee with that ID

**Example**
```bash
curl -i http://localhost:8080/api/1
```

### Get a list of all employees

`POST /api/add`
 - Body is JSON (without ID, it is automatically generated)

**Responses**
- `201 Created`, returns created employee
- `409 Conflict`, if emplyoee with that email already exists

**Example**
```bash
curl -i -X POST http://localhost:8080/api/add \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Robin",
        "lastName": "Hood",
        "emailAdress": "Robin@hood.com"
      }'
```

### Delete an employee

`DELETE /api/{id}`

**Responses**
- `204 No content`, employee deleted
-  `400 Bad reqiest`, trying to delete an employee that doesnt exist

**Example**
```bash
curl -i -X DELETE http://localhost:8080/api/1
```
