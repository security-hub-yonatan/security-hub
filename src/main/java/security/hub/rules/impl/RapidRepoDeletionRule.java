package security.hub.rules.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import security.hub.alert.AnomalyAlert;
import security.hub.model.GitHubEvent;
import security.hub.rules.AnomalyRule;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RapidRepoDeletionRule implements AnomalyRule {
    private final Map<String, Instant> creationTimes = new ConcurrentHashMap<>();

    @Override
    public Optional<AnomalyAlert> detectAnomaly(GitHubEvent event) {
        Optional<AnomalyAlert> anomalyAlert = Optional.empty();

        if (event.repository() == null) {
            return anomalyAlert;
        }

        String repoName = event.repository().fullName();
        Instant actionTime = event.repository().pushedAt();

        if ("created".equalsIgnoreCase(event.action())) {
            creationTimes.put(repoName, actionTime);
        } else if ("deleted".equalsIgnoreCase(event.action()) && creationTimes.containsKey(repoName)) {
            Instant created = creationTimes.remove(repoName);
            long minutes = Duration.between(created, actionTime).toMinutes();

            if (minutes < 10) {
                anomalyAlert = Optional.of(new AnomalyAlert(
                        String.format("Repository: %s was deleted less than 10 minutes after creation", repoName),
                        event
                ));
            }
        }

        return anomalyAlert;
    }

    @Scheduled(fixedDelay = 20 * 60 * 1000)
    public void cleanupOldEntries() {
        long cleanupThresholdMinutes = 20;
        Instant maxTimeThreshold = Instant.now().minus(Duration.ofMinutes(cleanupThresholdMinutes));
        creationTimes.entrySet().removeIf(entry -> entry.getValue().isBefore(maxTimeThreshold));
    }
}