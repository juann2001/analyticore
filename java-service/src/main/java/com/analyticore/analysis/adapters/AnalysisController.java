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

        // Processing should ideally be asynchronous to return 202 quickly,
        // but for simplicity in this prototype we can start it in a new thread
        // or rely on the python service's timeout handling. 
        // A better approach is using an ExecutorService.
        new Thread(() -> analysisService.processJob(jobId)).start();

        return ResponseEntity.accepted().build();
    }
}
