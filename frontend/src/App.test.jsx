import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

test('renders AnalytiCore title', () => {
  render(<App />);
  const titleElement = screen.getByText(/AnalytiCore/i);
  expect(titleElement).toBeInTheDocument();
});
