package top.whitecola.promodule.gui.notification;

import top.whitecola.promodule.gui.notification.beans.NotificationRenderEvent;

import java.util.Iterator;
import java.util.Vector;

public class NotificationManager implements INotificationManager{
    protected Vector<AbstractNotification> notifications = new Vector<AbstractNotification>();
    protected int line = 3;

    @Override
    public Vector<AbstractNotification> getNotifications() {
        return null;
    }

    @Override
    public void push(AbstractNotification notification) {
        notifications.add(notification);
        if(notifications.size()>line){
            for(int i=0;i<=notifications.size()-line;i++){
                notifications.get(i).setNeedLeave(true);
            }
        }


    }

    @Override
    public void renderAll() {
        Iterator<AbstractNotification> iterator = notifications.iterator();
        while(iterator.hasNext()){
            AbstractNotification notification = iterator.next();
            if(notification.getFinish()){
                iterator.remove();
            }
            notification.render(new NotificationRenderEvent(notifications.indexOf(notification),line));
        }
    }
}
