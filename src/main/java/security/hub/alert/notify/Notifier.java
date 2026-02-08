package security.hub.alert.notify;

import security.hub.alert.AnomalyAlert;

public interface Notifier {
    void notify(AnomalyAlert alert);
}