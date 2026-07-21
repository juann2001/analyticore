import httpx
import os
import logging

JAVA_SERVICE_URL = os.environ.get("JAVA_SERVICE_URL", "http://localhost:8080")

logger = logging.getLogger(__name__)

def request_analysis_from_java(job_id: str):
    """Calls the Java service synchronously to initiate processing."""
    url = f"{JAVA_SERVICE_URL}/analyze"
    payload = {"jobId": job_id}
    try:
        response = httpx.post(url, json=payload, timeout=5.0)
        response.raise_for_status()
        logger.info(f"Successfully requested analysis for job {job_id}")
    except Exception as e:
        logger.error(f"Failed to request analysis from Java service: {e}")
        # Note: Depending on requirements, we might want to retry or mark as ERROR here
