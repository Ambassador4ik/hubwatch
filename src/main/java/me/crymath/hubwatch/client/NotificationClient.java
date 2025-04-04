package me.crymath.hubwatch.client;

import java.util.List;
import me.crymath.hubwatch.model.Notification;

public interface NotificationClient {
    List<Notification> listNotifications(boolean all, boolean participating);
}
