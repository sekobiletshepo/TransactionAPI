# TransactionsAPI

A Spring Boot aggregated transactions API. The local profile uses an in-memory H2 database and loads demo data automatically.

## Run locally

1. Install Java 21 or later.
2. From the project folder, run:

   ```powershell
   .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
   ```

3. In Postman, import [`postman/TransactionsAPI.postman_collection.json`](./postman/TransactionsAPI.postman_collection.json) and [`postman/TransactionsAPI-SecondMockClient.postman_collection.json`](./postman/TransactionsAPI-SecondMockClient.postman_collection.json).
4. Send **Get category totals**. The sample transactions are loaded from `src/main/resources/db/mock-data.sql`.

The local database resets whenever the app restarts. The local profile allows unauthenticated access to the aggregate GET endpoint for practice; do not use it as a production security setup.

## Run with Docker

Build the image from the project root:

```powershell
docker build -t transactions-api .
```

Start the container:

```powershell
docker run --rm --name transactions-api -p 8080:8080 transactions-api
```

The image uses the `local` Spring profile by default, so the H2 demo database and mock transactions are loaded automatically. Test the running container with the Postman collections above or call:

```text
http://localhost:8080/api/v1/transactions/aggregates?from=2026-01-01&to=2026-01-31
```

## Aggregate endpoint

`GET http://localhost:8080/api/v1/transactions/aggregates`

Optional query parameters:

| Parameter | Example | Description |
|---|---|---|
| `from` | `2026-01-01` | Include transactions on or after this date |
| `to` | `2026-01-31` | Include transactions through this date |
| `accountId` | `1` | Filter by account |
| `categoryId` | `1` | Filter by category |

Example:

```text
http://localhost:8080/api/v1/transactions/aggregates?from=2026-01-01&to=2026-01-31
```

The local demo uses `userId = 1`. The endpoint currently supports reading aggregates; it does not provide API requests for creating accounts or transactions. The second collection filters the separate Demo savings account (ID `2`) and shows Entertainment and Health expenses.

## Test

```powershell
.\mvnw.cmd test
```
