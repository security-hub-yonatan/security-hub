package security.hub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubRepository(@JsonProperty("full_name") String fullName, @JsonProperty("pushed_at") Instant pushedAt) {
}
