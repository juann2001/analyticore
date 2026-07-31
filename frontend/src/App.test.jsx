import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

jest.mock('./adapters/useJob', () => ({
  useJob: () => ({ job: null, loading: false, error: null, submitJob: jest.fn() })
}));

test('renders AnalytiCore title', () => {
  render(<App />);
  const titleElement = screen.getByText(/AnalytiCore/i);
  expect(titleElement).toBeInTheDocument();
});
