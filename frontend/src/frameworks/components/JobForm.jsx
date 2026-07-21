import React, { useState } from 'react';

export function JobForm({ onSubmit, loading }) {
  const [text, setText] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (text.trim() && !loading) {
      onSubmit(text);
      setText('');
    }
  };

  return (
    <div className="card">
      <h2>Submit Text for Analysis</h2>
      <form onSubmit={handleSubmit}>
        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Enter text to analyze..."
          disabled={loading}
          rows={6}
          style={{ width: '100%', marginBottom: '1rem', padding: '0.5rem' }}
        />
        <button type="submit" disabled={!text.trim() || loading} className="btn-primary">
          {loading ? 'Submitting...' : 'Analyze Text'}
        </button>
      </form>
    </div>
  );
}
