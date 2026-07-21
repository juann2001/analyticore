from enum import Enum
from pydantic import BaseModel
from typing import List, Optional

class JobStatus(str, Enum):
    PENDIENTE = "PENDIENTE"
    PROCESANDO = "PROCESANDO"
    COMPLETADO = "COMPLETADO"
    ERROR = "ERROR"

class AnalysisResult(BaseModel):
    sentiment: str
    keywords: List[str]

class Job(BaseModel):
    id: str
    text: str
    status: JobStatus = JobStatus.PENDIENTE
    result: Optional[AnalysisResult] = None
