package security.hub.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import security.hub.detect.AnomalyDetector;
import security.hub.model.GitHubEvent;

@Log4j2
@RestController
public class WebhookController {

    private final AnomalyDetector anomalyDetector;

    public WebhookController(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestHeader("X-GitHub-Event") String eventTypeHeader, @RequestBody GitHubEvent event) {
        GitHubEvent eventWithType = event.withEventType(eventTypeHeader);

        try {
            anomalyDetector.detectAnomaly(eventWithType);

            return ResponseEntity.ok().body("Anomaly process succeeded");
        } catch (Exception exception) {
            String errorMessage = "The following occurred while detecting Anomaly: ";
            log.error(errorMessage, exception);

            return ResponseEntity.internalServerError().body(errorMessage + exception.getMessage());
        }
    }
}