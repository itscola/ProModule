package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinRenderManager;

@Mixin(RenderManager.class)
public class MixinRenderManager implements IMixinRenderManager {
    @Shadow
    private double renderPosX;

    @Shadow
    private double renderPosY;

    @Shadow
    private double renderPosZ;

    @Override
    public double getRenderPosX() {
        return this.renderPosX;
    }

    @Override
    public double getRenderPosY() {
        return this.renderPosY;
    }

    @Override
    public double getRenderPosZ() {
        return this.renderPosZ;
    }
}
