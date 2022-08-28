package top.whitecola.promodule.gui.notification.beans;

public class NotificationRenderEvent {
    private int level;
    private int totalLine;

    public NotificationRenderEvent(int level,int totalLine){
        this.level = level;
        this.totalLine = totalLine;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }
}
