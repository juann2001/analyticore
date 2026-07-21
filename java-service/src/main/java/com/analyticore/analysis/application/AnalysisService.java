package com.analyticore.analysis.application;

import com.analyticore.analysis.domain.AnalysisResult;
import com.analyticore.analysis.domain.JobStatus;
import com.analyticore.analysis.infrastructure.JobEntity;
import com.analyticore.analysis.infrastructure.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnalysisService {

    private final JobRepository jobRepository;
    private final ObjectMapper objectMapper;

    public AnalysisService(JobRepository jobRepository, ObjectMapper objectMapper) {
        this.jobRepository = jobRepository;
        this.objectMapper = objectMapper;
    }

    public void processJob(String jobId) {
        Optional<JobEntity> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isEmpty()) return;

        JobEntity job = optionalJob.get();
        if (job.getStatus() != JobStatus.PENDIENTE) return;

        // Update to PROCESSING
        job.setStatus(JobStatus.PROCESANDO);
        jobRepository.save(job);

        try {
            // Simulate processing time
            Thread.sleep(3000);

            // Perform simple analysis
            String text = job.getText().toLowerCase();
            String sentiment = analyzeSentiment(text);
            List<String> keywords = extractKeywords(text);
            
            AnalysisResult result = new AnalysisResult(sentiment, keywords);
            String resultJson = objectMapper.writeValueAsString(result);

            job.setResult(resultJson);
            job.setStatus(JobStatus.COMPLETADO);
        } catch (Exception e) {
            job.setStatus(JobStatus.ERROR);
            job.setResult("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            jobRepository.save(job);
        }
    }

    private String analyzeSentiment(String text) {
        String lowerText = text.toLowerCase();
        
        boolean containsBut = lowerText.contains("pero ") || lowerText.contains("sin embargo") || lowerText.contains("aunque");

        int positiveScore = 0;
        int negativeScore = 0;

        if (lowerText.contains("excelente") || lowerText.contains("buen") || lowerText.contains("feliz") || lowerText.contains("genial") || lowerText.contains("increíble")) {
            positiveScore++;
        }
        
        if (lowerText.contains("mal") || lowerText.contains("horrible") || lowerText.contains("triste") || lowerText.contains("pésimo") || lowerText.contains("pesimo") || lowerText.contains("terrible") || lowerText.contains("decepcionante") || lowerText.contains("pésima") || lowerText.contains("pesima")) {
            negativeScore++;
        }

        if (positiveScore > 0 && containsBut) {
            return "NEUTRAL";
        }

        if (positiveScore > negativeScore) {
            return "POSITIVE";
        } else if (negativeScore > positiveScore) {
            return "NEGATIVE";
        }
        return "NEUTRAL";
    }

    private List<String> extractKeywords(String text) {
        // Simple mock extraction: just split by spaces and take a few words
        String[] words = text.split("\\s+");
        return Arrays.asList(words).subList(0, Math.min(words.length, 5));
    }
}
