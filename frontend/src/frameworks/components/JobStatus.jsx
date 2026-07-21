import React from 'react';
import { JobStatus as StatusEnum } from '../../domain/Job';

export function JobStatus({ job }) {
  if (!job) return null;

  const getStatusColor = () => {
    switch (job.status) {
      case StatusEnum.COMPLETED:
        return 'green';
      case StatusEnum.ERROR:
        return 'red';
      case StatusEnum.PROCESSING:
        return 'blue';
      default:
        return 'gray';
    }
  };

  return (
    <div className="card status-card">
      <h3>Job Status</h3>
      <p><strong>ID:</strong> {job.id}</p>
      <p>
        <strong>Status:</strong>{' '}
        <span style={{ color: getStatusColor(), fontWeight: 'bold' }}>
          {job.status}
        </span>
      </p>
      {(job.status === StatusEnum.PENDING || job.status === StatusEnum.PROCESSING) && (
        <div className="spinner"></div>
      )}
    </div>
  );
}
