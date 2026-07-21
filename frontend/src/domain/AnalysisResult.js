export class AnalysisResult {
  constructor({ sentiment, keywords }) {
    this.sentiment = sentiment; // e.g., 'POSITIVE', 'NEGATIVE', 'NEUTRAL'
    this.keywords = keywords || []; // array of strings
  }
}
