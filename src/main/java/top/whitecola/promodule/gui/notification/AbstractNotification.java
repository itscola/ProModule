package top.whitecola.promodule.gui.notification;


import top.whitecola.promodule.common.Position;

public abstract class AbstractNotification implements INotificationPos, INotificationStyle, INotificationControl {
    protected Position pos;
    protected Position toPos;


    protected boolean isFinish;
    protected boolean needLeave;
    protected boolean Entered;

    protected int level;

    protected long lastTime;



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
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public Position getPos() {
        return pos;
    }

    @Override
    public Position getToPos() {
        return toPos;
    }

    @Override
    public void setPos(Position pos) {
        this.pos = pos;
    }

    @Override
    public void setToPos(Position toPos) {
        this.toPos = toPos;
    }

    public boolean isEntered() {
        return Entered;
    }

    public void setEntered(boolean entered) {
        Entered = entered;
    }
}
