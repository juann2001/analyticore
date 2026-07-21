import React from 'react';
import { JobStatus as StatusEnum } from '../../domain/Job';

export function JobResult({ job }) {
  if (!job || job.status !== StatusEnum.COMPLETED || !job.result) return null;

  const { sentiment, keywords } = job.result;

  return (
    <div className="card result-card">
      <h3>Resultados del Análisis</h3>
      
      <div className="result-section">
        <h4>Sentimiento</h4>
        <span className={`sentiment-badge ${sentiment.toLowerCase()}`}>
          {sentiment}
        </span>
      </div>

      <div className="result-section">
        <h4>Palabras clave</h4>
        {keywords && keywords.length > 0 ? (
          <div className="keywords-list">
            {keywords.map((kw, i) => (
              <span key={i} className="keyword-tag">{kw}</span>
            ))}
          </div>
        ) : (
          <p>No se extrajeron palabras clave.</p>
        )}
      </div>
    </div>
  );
}
