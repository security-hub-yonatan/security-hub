package security.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubEvent(String action, String eventType, GitHubSender sender, GitHubRepository repository, GitHubTeam team) {
    public GitHubEvent withEventType(String eventType) {
        return new GitHubEvent(this.action, eventType, this.sender, this.repository, this.team);
    }
}