package top.whitecola.promodule.events.impls.event;

public class WorldRenderEvent extends AbstractEvent{
    private float partialTicks;

    public WorldRenderEvent(float partialTicks){
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
