package com.analyticore.analysis.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.analyticore.analysis.domain.JobStatus status;

    @Column(columnDefinition = "TEXT")
    private String result;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public com.analyticore.analysis.domain.JobStatus getStatus() { return status; }
    public void setStatus(com.analyticore.analysis.domain.JobStatus status) { this.status = status; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
