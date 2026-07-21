import { Job } from '../domain/Job';
import { AnalysisResult } from '../domain/AnalysisResult';

export class JobService {
  constructor(apiClient) {
    this.apiClient = apiClient;
  }

  async submitJob(text) {
    const response = await this.apiClient.post('/jobs', { text });
    return new Job({ id: response.jobId, text, status: 'PENDIENTE' });
  }

  async getJobStatus(jobId) {
    const response = await this.apiClient.get(`/jobs/${jobId}`);
    
    let result = null;
    if (response.result) {
        result = new AnalysisResult(response.result);
    }

    return new Job({
      id: response.jobId,
      status: response.status,
      result: result
    });
  }
}
