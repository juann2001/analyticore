import React from 'react'
import { JobForm } from './frameworks/components/JobForm'
import { JobStatus } from './frameworks/components/JobStatus'
import { JobResult } from './frameworks/components/JobResult'
import { useJob } from './adapters/useJob'

function App() {
  const { job, loading, error, submitJob } = useJob();

  return (
    <div className="container">
      <header className="header">
        <h1>AnalytiCore</h1>
        <p>Plataforma de Análisis de Texto</p>
      </header>

      <main className="main-content">
        {error && <div className="error-message">Error: {error}</div>}
        
        <JobForm onSubmit={submitJob} loading={loading} />
        
        {job && (
          <div className="results-container">
            <JobStatus job={job} />
            <JobResult job={job} />
          </div>
        )}
      </main>
    </div>
  )
}

export default App
