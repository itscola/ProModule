package top.whitecola.promodule.gui.notification;

import java.util.Vector;

public interface INotificationManager {
    Vector<AbstractNotification> getNotifications();
    void push(AbstractNotification notification);
    void renderAll();
}
