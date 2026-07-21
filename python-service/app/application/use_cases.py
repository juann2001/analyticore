import uuid
import json
from sqlalchemy.orm import Session
from app.domain.models import Job, JobStatus, AnalysisResult
from app.infrastructure.database import JobModel, DBJobStatus
from app.infrastructure.java_client import request_analysis_from_java

def create_job(text: str, db: Session) -> Job:
    job_id = str(uuid.uuid4())
    db_job = JobModel(id=job_id, text=text, status=DBJobStatus.PENDING)
    
    db.add(db_job)
    db.commit()
    db.refresh(db_job)
    
    # Trigger Java service synchronously
    request_analysis_from_java(job_id)

    return Job(id=job_id, text=text, status=JobStatus.PENDING)

def get_job_status(job_id: str, db: Session) -> Job:
    db_job = db.query(JobModel).filter(JobModel.id == job_id).first()
    if not db_job:
        return None
    
    result_obj = None
    if db_job.result:
        try:
            result_data = json.loads(db_job.result)
            result_obj = AnalysisResult(**result_data)
        except Exception:
            pass

    return Job(
        id=db_job.id,
        text=db_job.text,
        status=JobStatus(db_job.status.value),
        result=result_obj
    )
