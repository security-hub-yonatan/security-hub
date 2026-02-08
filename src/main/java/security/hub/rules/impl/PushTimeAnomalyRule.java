package security.hub.rules.impl;

import org.springframework.stereotype.Component;
import security.hub.alert.AnomalyAlert;
import security.hub.model.GitHubEvent;
import security.hub.rules.AnomalyRule;

import java.time.ZoneId;
import java.util.Optional;

@Component
public class PushTimeAnomalyRule implements AnomalyRule {
    private final ZoneId ZONE = ZoneId.of("Asia/Jerusalem");

    @Override
    public Optional<AnomalyAlert> detect(GitHubEvent event) {
        Optional<AnomalyAlert> anomalyAlert = Optional.empty();

        if (event.repository() == null || !"push".equalsIgnoreCase(event.eventType())) {
            return anomalyAlert;
        }

        int hour = event.repository().pushedAt().atZone(ZONE).getHour();

        int startSuspiciousTime = 14;
        int endSuspiciousTime = 16;

        if (hour >= startSuspiciousTime && hour < endSuspiciousTime) {
            anomalyAlert = Optional.of(new AnomalyAlert(
                    String.format("Push detected between %d:00 and %d:00", startSuspiciousTime, endSuspiciousTime),
                    event
            ));
        }

        return anomalyAlert;
    }
}