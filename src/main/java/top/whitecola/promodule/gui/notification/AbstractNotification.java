package top.whitecola.promodule.gui.notification;


public abstract class AbstractNotification implements INotificationPos, INotificationStyle, INotificationControl {
    protected float x;
    protected float y;
    protected float x2;
    protected float y2;

    protected boolean isFinish;
    protected boolean needLeave;


//    @Override
//    public void render() {
////        Render2DUtils.drawRoundedRect2(x,y,x2,y2,2);
//    }


    @Override
    public void setNeedLeave(boolean needLeave) {
        this.needLeave = needLeave;
    }

    @Override
    public boolean getNeedLeave() {
        return false;
    }

    @Override
    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    @Override
    public boolean getFinish() {
        return false;
    }



    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setX2(float x2) {
        this.x2 = x2;
    }

    @Override
    public void setY2(float y2) {
        this.y2 = y2;
    }

    @Override
    public float getY2() {
        return y2;
    }

    @Override
    public float getX2() {
        return x2;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }


}
