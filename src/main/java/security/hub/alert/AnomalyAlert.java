package security.hub.alert;

import security.hub.model.GitHubEvent;

public record AnomalyAlert(String message, GitHubEvent event) {
}