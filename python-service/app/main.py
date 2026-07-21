from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.adapters.api import router
from app.infrastructure.database import init_db
import logging

logging.basicConfig(level=logging.INFO)

app = FastAPI(title="AnalytiCore Submission Service")

# Allow CORS for frontend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(router)

@app.on_event("startup")
def on_startup():
    init_db()
    
@app.get("/health")
def health_check():
    return {"status": "ok"}
