package security.hub.rules.impl;

import org.springframework.stereotype.Component;
import security.hub.alert.AnomalyAlert;
import security.hub.model.GitHubEvent;
import security.hub.rules.AnomalyRule;

import java.util.List;
import java.util.Optional;

@Component
public class TeamCreatedAnomalyRule implements AnomalyRule {
    private static final List<String> FORBIDDEN_PREFIXES = List.of("hacker");

    @Override
    public Optional<AnomalyAlert> detectAnomaly(GitHubEvent event) {
        Optional<AnomalyAlert> anomalyAlert = Optional.empty();

        if (!"created".equalsIgnoreCase(event.action()) || event.team() == null || !"team".equalsIgnoreCase(event.eventType())) {
            return anomalyAlert;
        }

        String teamName = event.team().name().toLowerCase();

        boolean suspicious = FORBIDDEN_PREFIXES.stream().anyMatch(teamName::startsWith);

        if (suspicious) {
            anomalyAlert = Optional.of(new AnomalyAlert(
                    String.format("New team: %s created with suspicious 'hacker' prefix", teamName),
                    event
            ));
        }

        return anomalyAlert;
    }
}