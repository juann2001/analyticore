package com.analyticore.analysis.domain;

import java.util.List;

public class AnalysisResult {
    private String sentiment;
    private List<String> keywords;

    public AnalysisResult() {}

    public AnalysisResult(String sentiment, List<String> keywords) {
        this.sentiment = sentiment;
        this.keywords = keywords;
    }

    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
}
