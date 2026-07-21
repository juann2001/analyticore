import os
from sqlalchemy import create_engine, Column, String, Text, Enum
from sqlalchemy.orm import sessionmaker, declarative_base
import enum
import json

DATABASE_URL = os.environ.get("DATABASE_URL", "postgresql://postgres:password@localhost:5432/postgres")

engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

class DBJobStatus(enum.Enum):
    PENDIENTE = "PENDIENTE"
    PROCESANDO = "PROCESANDO"
    COMPLETADO = "COMPLETADO"
    ERROR = "ERROR"

class JobModel(Base):
    __tablename__ = "jobs"

    id = Column(String, primary_key=True, index=True)
    text = Column(Text, nullable=False)
    status = Column(String, default=DBJobStatus.PENDIENTE.value)
    result = Column(Text, nullable=True) # Stored as JSON string

def init_db():
    Base.metadata.create_all(bind=engine)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
