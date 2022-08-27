package top.whitecola.promodule.gui.notification;

public interface INotificationControl {
    void setFinish(boolean isFinish);
    boolean getFinish();

    void setNeedLeave(boolean needLeave);
    boolean getNeedLeave();

}