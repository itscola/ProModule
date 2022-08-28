package top.whitecola.promodule.gui.notification.impls;

import top.whitecola.promodule.gui.notification.AbstractNotification;
import top.whitecola.promodule.gui.notification.beans.NotificationRenderEvent;

public class PureNotification extends AbstractNotification {
    protected long totalTime = 1000;

    public PureNotification(){
        
    }

    @Override
    public void render(NotificationRenderEvent e) {
        manageTime(e.getLevel());
        if(!isEntered()){

        }
    }

    public void manageTime(int level){
        if(lastTime==0){
            if(level==1){
                lastTime = System.currentTimeMillis();
            }
            return;
        }

        if(System.currentTimeMillis() - this.lastTime>=totalTime){
            this.setNeedLeave(true);
        }
    };
}
