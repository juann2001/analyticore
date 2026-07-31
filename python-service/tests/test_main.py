import pytest
import os
os.environ["DATABASE_URL"] = "sqlite:///./test.db"

from httpx import AsyncClient, ASGITransport
from app.main import app
from app.infrastructure.database import init_db

# Initialize DB for tests
init_db()

@pytest.mark.asyncio
async def test_health_check():
    async with AsyncClient(transport=ASGITransport(app=app), base_url="http://test") as ac:
        response = await ac.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "ok"}

@pytest.mark.asyncio
async def test_create_and_get_job():
    async with AsyncClient(transport=ASGITransport(app=app), base_url="http://test") as ac:
        # Test Create
        response = await ac.post("/jobs", json={"text": "Test submission"})
        assert response.status_code == 200
        data = response.json()
        assert "jobId" in data
        assert data["status"] == "PENDIENTE"
        job_id = data["jobId"]
        
        # Test Get
        response_get = await ac.get(f"/jobs/{job_id}")
        assert response_get.status_code == 200
        data_get = response_get.json()
        assert data_get["jobId"] == job_id
        assert data_get["status"] == "PENDIENTE"
