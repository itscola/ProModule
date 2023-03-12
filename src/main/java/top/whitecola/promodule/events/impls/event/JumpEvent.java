package top.whitecola.promodule.events.impls.event;

public class JumpEvent extends AbstractEvent{
    private double height;

    public JumpEvent(double height) {
        this.height = height;
    }

    public JumpEvent() {
    }

    public void setHeight(double var1) {
        this.height = var1;
    }

    public double getHeight() {
        return this.height;
    }
}
