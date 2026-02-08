package security.hub.detect;

import org.springframework.stereotype.Component;
import security.hub.alert.notify.Notifier;
import security.hub.model.GitHubEvent;
import security.hub.rules.AnomalyRule;

import java.util.List;
import java.util.Optional;

@Component
public class AnomalyDetector {
    private final List<AnomalyRule> rules;
    private final List<Notifier> notifiers;

    public AnomalyDetector(List<AnomalyRule> rules, List<Notifier> notifiers) {
        this.rules = rules;
        this.notifiers = notifiers;
    }

    public void detectAnomaly(GitHubEvent event) {
        rules.parallelStream()
                .map(rule -> rule.detectAnomaly(event))
                .flatMap(Optional::stream)
                .forEach(alert -> notifiers.forEach(notifier -> notifier.notify(alert)));
    }
}