package com.analyticore.analysis.domain;

public class Job {
    private String id;
    private String text;
    private JobStatus status;
    private AnalysisResult result;

    public Job() {}

    public Job(String id, String text, JobStatus status) {
        this.id = id;
        this.text = text;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public AnalysisResult getResult() { return result; }
    public void setResult(AnalysisResult result) { this.result = result; }
}
