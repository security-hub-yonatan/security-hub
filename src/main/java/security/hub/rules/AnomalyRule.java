package security.hub.rules;

import security.hub.alert.AnomalyAlert;
import security.hub.model.GitHubEvent;

import java.util.Optional;

public interface AnomalyRule {
    Optional<AnomalyAlert> detect(GitHubEvent event);
}