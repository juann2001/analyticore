package com.analyticore.analysis.adapters;

import com.analyticore.analysis.application.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Void> analyze(@RequestBody Map<String, String> payload) {
        String jobId = payload.get("jobId");
        if (jobId == null || jobId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Processing will be asynchronous using @Async in the service
        analysisService.processJob(jobId);

        return ResponseEntity.accepted().build();
    }
}
