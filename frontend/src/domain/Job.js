export const JobStatus = {
  PENDING: 'PENDIENTE',
  PROCESSING: 'PROCESANDO',
  COMPLETED: 'COMPLETADO',
  ERROR: 'ERROR'
};

export class Job {
  constructor({ id, text, status = JobStatus.PENDING, result = null }) {
    this.id = id;
    this.text = text;
    this.status = status;
    this.result = result;
  }
}
