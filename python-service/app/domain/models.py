from enum import Enum
from pydantic import BaseModel
from typing import List, Optional

class JobStatus(str, Enum):
    PENDING = "PENDIENTE"
    PROCESSING = "PROCESANDO"
    COMPLETED = "COMPLETADO"
    ERROR = "ERROR"

class AnalysisResult(BaseModel):
    sentiment: str
    keywords: List[str]

class Job(BaseModel):
    id: str
    text: str
    status: JobStatus = JobStatus.PENDING
    result: Optional[AnalysisResult] = None
