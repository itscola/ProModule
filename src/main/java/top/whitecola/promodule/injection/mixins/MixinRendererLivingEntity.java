package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.shader.Shader;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.impls.render.DamageColor;

import java.nio.FloatBuffer;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> {


    @Shadow
    protected abstract <T extends EntityLivingBase> int getColorMultiplier(T p_getColorMultiplier_1_, float p_getColorMultiplier_2_, float p_getColorMultiplier_3_);

    @Shadow protected FloatBuffer brightnessBuffer;

    @Shadow @Final
    private static DynamicTexture field_177096_e;

    @Inject(method = "doRender", at = @At(value = "HEAD"))
    public <T extends EntityLivingBase> void HeadDoRender(T p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_5_, float p_doRender_6_, float p_doRender_7_, CallbackInfo ci){
        if(ProModule.getProModule().getModuleManager().getModuleByName("Chams").isEnabled()){
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F,-1000000F);
        }
    }

    @Inject(method = "doRender", at = @At(value = "RETURN"))
    public <T extends EntityLivingBase> void RETURNDoRender(T p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_5_, float p_doRender_6_, float p_doRender_7_, CallbackInfo ci) {
        if (ProModule.getProModule().getModuleManager().getModuleByName("Chams").isEnabled()) {

            GL11.glPolygonOffset(1.0F, 1000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }

    }


    /**
     * @author White_cola
     * @reason DamageColor Module.
     */
    @Overwrite
    protected boolean setBrightness(T p_setBrightness_1_, float p_setBrightness_2_, boolean p_setBrightness_3_) {
        float f = p_setBrightness_1_.getBrightness(p_setBrightness_2_);
        int i = this.getColorMultiplier(p_setBrightness_1_, f, p_setBrightness_2_);
        boolean flag = (i >> 24 & 255) > 0;
        boolean flag1 = p_setBrightness_1_.hurtTime > 0 || p_setBrightness_1_.deathTime > 0;
        if (!flag && !flag1) {
            return false;
        } else if (!flag && !p_setBrightness_3_) {
            return false;
        } else {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            this.brightnessBuffer.position(0);
            if (flag1) {

                DamageColor damageColor = (DamageColor) ProModule.getProModule().getModuleManager().getModuleByName("DamageColor");
                if(damageColor!=null&&damageColor.isEnabled()){

                    this.brightnessBuffer.put(damageColor.getRed());
                    this.brightnessBuffer.put(damageColor.getGreen());
                    this.brightnessBuffer.put(damageColor.getBlue());
                    this.brightnessBuffer.put(damageColor.getAlpha());
                }else {
                    this.brightnessBuffer.put(1.0F);
                    this.brightnessBuffer.put(0.0F);
                    this.brightnessBuffer.put(0.0F);
                    this.brightnessBuffer.put(0.3F);
                }


            } else {
                float f1 = (float)(i >> 24 & 255) / 255.0F;
                float f2 = (float)(i >> 16 & 255) / 255.0F;
                float f3 = (float)(i >> 8 & 255) / 255.0F;
                float f4 = (float)(i & 255) / 255.0F;
                this.brightnessBuffer.put(f2);
                this.brightnessBuffer.put(f3);
                this.brightnessBuffer.put(f4);
                this.brightnessBuffer.put(1.0F - f1);
            }

            this.brightnessBuffer.flip();
            GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(field_177096_e.getGlTextureId());
            GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
    }

}
