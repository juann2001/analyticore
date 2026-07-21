# AnalytiCore

AnalytiCore is a robust, microservices-based text analysis platform.

## Architecture

This platform follows a Service-Oriented Architecture (SOA) and consists of four main components:
1. **Frontend (React)**: A Single Page Application (SPA) built with Vite and React. Implements Clean Architecture.
2. **Submission Service (Python)**: A FastAPI service that receives text analysis requests, saves them in PostgreSQL as `PENDIENTE`, and notifies the Java service. Implements Clean Architecture.
3. **Analysis Service (Java)**: A Spring Boot application that performs the actual text analysis asynchronously, updating the status in PostgreSQL to `PROCESANDO` and finally `COMPLETADO` or `ERROR`. Implements Clean Architecture.
4. **Database**: PostgreSQL database acting as the single source of truth for job statuses and results.

All services are stateless; all state is persisted in the PostgreSQL database.

## Local Development (Docker Compose)

To run the entire platform locally, ensure you have Docker and Docker Compose installed.

1. Clone the repository.
2. Ensure ports `8000`, `8080`, `3000`, and `5432` are available.
3. Run the following command in the root directory:

   ```bash
   docker-compose up -d --build
   ```

4. The services will be available at:
   - Frontend: http://localhost:3000
   - Python API: http://localhost:8000
   - Java API: http://localhost:8080

## Deployment on Render

This project is designed to be easily deployed on Render as 3 independent Web Services and 1 Managed PostgreSQL Database.

### 1. PostgreSQL Database
- Create a new PostgreSQL database on Render.
- Note the Internal Database URL (for the other services to connect).

### 2. Java Service
- Create a New Web Service.
- Connect to this repository.
- **Root Directory**: `java-service`
- **Environment**: `Docker`
- **Environment Variables**:
  - `DATABASE_URL`: `jdbc:postgresql://<RENDER_INTERNAL_DB_HOST>/<DB_NAME>`
  - `DB_USERNAME`: `<DB_USER>`
  - `DB_PASSWORD`: `<DB_PASSWORD>`

### 3. Python Service
- Create a New Web Service.
- Connect to this repository.
- **Root Directory**: `python-service`
- **Environment**: `Docker`
- **Environment Variables**:
  - `DATABASE_URL`: `<RENDER_INTERNAL_DB_URL>` (format: `postgresql://user:pass@host/db`)
  - `JAVA_SERVICE_URL`: `https://<YOUR_JAVA_SERVICE_RENDER_URL>`

### 4. Frontend Service
- Create a New Web Service.
- Connect to this repository.
- **Root Directory**: `frontend`
- **Environment**: `Docker`
- **Environment Variables**:
  - `VITE_API_URL`: `https://<YOUR_PYTHON_SERVICE_RENDER_URL>` (Make sure to configure CORS appropriately in the Python service for production).
