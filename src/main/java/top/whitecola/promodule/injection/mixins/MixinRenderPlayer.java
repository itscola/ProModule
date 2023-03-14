package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.impls.render.ESP;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {
    public MixinRenderPlayer(RenderManager p_i46156_1_, ModelBase p_i46156_2_, float p_i46156_3_) {
        super(p_i46156_1_, p_i46156_2_, p_i46156_3_);
    }

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci){
        if(ProModule.getProModule().getModuleManager().getModuleByClass(ESP.class).brightPlayer){
            if(!entity.isUser()&&this.renderManager.livingPlayer==entity){
                GlStateManager.disableLighting();
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractClientPlayer abstractClientPlayer) {
        return null;
    }
}
