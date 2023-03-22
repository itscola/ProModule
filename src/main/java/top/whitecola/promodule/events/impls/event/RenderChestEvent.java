package top.whitecola.promodule.events.impls.event;

import net.minecraft.tileentity.TileEntityChest;

public class RenderChestEvent extends AbstractEvent{
    private final TileEntityChest entity;
    private final Runnable chestRenderer;

    public RenderChestEvent(TileEntityChest entity, Runnable chestRenderer) {
        this.entity = entity;
        this.chestRenderer = chestRenderer;
    }

    public TileEntityChest getEntity() {
        return entity;
    }

    public void drawChest() {
        this.chestRenderer.run();
    }
}
