package com.analyticore.analysis;

import com.analyticore.analysis.application.AnalysisService;
import com.analyticore.analysis.infrastructure.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AnalysisServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AnalysisService analysisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActuatorHealthEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"status\":\"UP\""));
    }

    @Test
    void testAnalyzeSentiment() {
        String result = ReflectionTestUtils.invokeMethod(analysisService, "analyzeSentiment", "Este producto es excelente");
        assertEquals("POSITIVE", result);

        String resultNegative = ReflectionTestUtils.invokeMethod(analysisService, "analyzeSentiment", "Este producto es horrible");
        assertEquals("NEGATIVE", resultNegative);
    }

    @Test
    void testExtractKeywords() {
        List<String> keywords = ReflectionTestUtils.invokeMethod(analysisService, "extractKeywords", "palabra1 palabra2 palabra3 palabra4 palabra5 palabra6");
        assertEquals(5, keywords.size());
        assertEquals("palabra1", keywords.get(0));
    }
}
