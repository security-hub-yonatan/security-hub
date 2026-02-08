package security.hub.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import security.hub.detect.AnomalyDetector;
import security.hub.model.GitHubEvent;

@RestController
public class WebhookController {

    private final AnomalyDetector anomalyDetector;

    public WebhookController(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestHeader("X-GitHub-Event") String eventTypeHeader, @RequestBody GitHubEvent event) {
        GitHubEvent eventWithType = event.withEventType(eventTypeHeader);
        anomalyDetector.detectAnomaly(eventWithType);
    }
}