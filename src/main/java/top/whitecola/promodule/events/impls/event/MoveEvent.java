package top.whitecola.promodule.events.impls.event;

public class MoveEvent extends AbstractEvent{
    public double x;
    public double y;
    public double z;
    private boolean onGround;

    public MoveEvent(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
