from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from pydantic import BaseModel
from typing import Optional, List
from app.infrastructure.database import get_db
from app.application import use_cases

router = APIRouter()

class CreateJobRequest(BaseModel):
    text: str

class AnalysisResultResponse(BaseModel):
    sentiment: str
    keywords: List[str]

class JobResponse(BaseModel):
    jobId: str
    status: str
    result: Optional[AnalysisResultResponse] = None

@router.post("/jobs", response_model=JobResponse)
def create_job(req: CreateJobRequest, db: Session = Depends(get_db)):
    job = use_cases.create_job(req.text, db)
    return JobResponse(jobId=job.id, status=job.status.value, result=None)

@router.get("/jobs/{job_id}", response_model=JobResponse)
def get_job(job_id: str, db: Session = Depends(get_db)):
    job = use_cases.get_job_status(job_id, db)
    if not job:
        raise HTTPException(status_code=404, detail="Job not found")
    
    result_dict = None
    if job.result:
        result_dict = AnalysisResultResponse(sentiment=job.result.sentiment, keywords=job.result.keywords)
        
    return JobResponse(jobId=job.id, status=job.status.value, result=result_dict)
