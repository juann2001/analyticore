import { useState, useEffect, useCallback } from 'react';
import { JobService } from '../application/jobService';
import { apiClient } from './apiClient';
import { JobStatus } from '../domain/Job';

const jobService = new JobService(apiClient);

export function useJob() {
  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const submitJob = async (text) => {
    setLoading(true);
    setError(null);
    try {
      const newJob = await jobService.submitJob(text);
      setJob(newJob);
    } catch (err) {
      setError(err.message);
      setJob(null);
    } finally {
      setLoading(false);
    }
  };

  const pollStatus = useCallback(async () => {
    if (!job || !job.id || job.status === JobStatus.COMPLETED || job.status === JobStatus.ERROR) {
      return;
    }

    try {
      const updatedJob = await jobService.getJobStatus(job.id);
      setJob(updatedJob);
    } catch (err) {
      setError(err.message);
      setJob(prev => ({ ...prev, status: JobStatus.ERROR }));
    }
  }, [job]);

  useEffect(() => {
    let intervalId;
    if (job && (job.status === JobStatus.PENDING || job.status === JobStatus.PROCESSING)) {
      intervalId = setInterval(() => {
        pollStatus();
      }, 2000); // Poll every 2 seconds
    }
    return () => {
      if (intervalId) clearInterval(intervalId);
    };
  }, [job, pollStatus]);

  return {
    job,
    loading,
    error,
    submitJob
  };
}
