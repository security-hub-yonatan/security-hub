package security.hub.alert.notify.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import security.hub.alert.AnomalyAlert;
import security.hub.alert.notify.Notifier;

@Log4j2
@Component
public class ConsoleNotifier implements Notifier {
    @Override
    public void notify(AnomalyAlert alert) {
        log.warn(">>> ALERT [" + alert.message() + "]");
        log.warn("User: " + alert.event().sender().username());
    }
}