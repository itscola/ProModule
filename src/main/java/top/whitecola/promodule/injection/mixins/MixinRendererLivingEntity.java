package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.shader.Shader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
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
import top.whitecola.promodule.utils.wrapper.RotationPitchHead;

import java.nio.FloatBuffer;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {


    protected MixinRendererLivingEntity(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Shadow
    protected abstract <T extends EntityLivingBase> int getColorMultiplier(T p_getColorMultiplier_1_, float p_getColorMultiplier_2_, float p_getColorMultiplier_3_);

    @Shadow protected FloatBuffer brightnessBuffer;

    @Shadow @Final
    private static DynamicTexture field_177096_e;

//    @Inject(method = "doRender", at = @At(value = "HEAD"))
//    public <T extends EntityLivingBase> void HeadDoRender(T p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_5_, float p_doRender_6_, float p_doRender_7_, CallbackInfo ci){
//        if(ProModule.getProModule().getModuleManager().getModuleByName("Chams").isEnabled()){
//            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
//            GL11.glPolygonOffset(1.0F,-1000000F);
//        }
//    }

//    @Inject(method = "doRender", at = @At(value = "RETURN"))
//    public <T extends EntityLivingBase> void RETURNDoRender(T p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_5_, float p_doRender_6_, float p_doRender_7_, CallbackInfo ci) {
//        if (ProModule.getProModule().getModuleManager().getModuleByName("Chams").isEnabled()) {
//
//            GL11.glPolygonOffset(1.0F, 1000000F);
//            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
//        }
//
//    }


    @Shadow protected ModelBase mainModel;

    @Shadow protected abstract float getSwingProgress(T p_getSwingProgress_1_, float p_getSwingProgress_2_);

    @Shadow protected abstract float interpolateRotation(float p_interpolateRotation_1_, float p_interpolateRotation_2_, float p_interpolateRotation_3_);

    @Shadow protected abstract void renderLivingAt(T p_renderLivingAt_1_, double p_renderLivingAt_2_, double p_renderLivingAt_4_, double p_renderLivingAt_4_2);

    @Shadow protected abstract float handleRotationFloat(T p_handleRotationFloat_1_, float p_handleRotationFloat_2_);

    @Shadow protected abstract void rotateCorpse(T p_rotateCorpse_1_, float p_rotateCorpse_2_, float p_rotateCorpse_3_, float p_rotateCorpse_4_);

    @Shadow protected abstract void preRenderCallback(T p_preRenderCallback_1_, float p_preRenderCallback_2_);

    @Shadow protected boolean renderOutlines;

    @Shadow protected abstract boolean setScoreTeamColor(T p_setScoreTeamColor_1_);

    @Shadow protected abstract void renderModel(T p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_, float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_);

    @Shadow protected abstract void unsetScoreTeamColor();

    @Shadow protected abstract boolean setDoRenderBrightness(T p_setDoRenderBrightness_1_, float p_setDoRenderBrightness_2_);

    @Shadow protected abstract void unsetBrightness();

    @Shadow protected abstract void renderLayers(T p_renderLayers_1_, float p_renderLayers_2_, float p_renderLayers_3_, float p_renderLayers_4_, float p_renderLayers_5_, float p_renderLayers_6_, float p_renderLayers_7_, float p_renderLayers_8_);

    /**
     * @author white_cola
     * @reason pitchRotation
     */
    @Overwrite
    public void doRender(T p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_6_, float p_doRender_8_, float p_doRender_9_) {

        if(ProModule.getProModule().getModuleManager().getModuleByName("Chams").isEnabled()){
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F,-1000000F);
        }

        if (!MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre(p_doRender_1_, (RendererLivingEntity) (Object)this, p_doRender_2_, p_doRender_4_, p_doRender_6_))) {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            this.mainModel.swingProgress = this.getSwingProgress(p_doRender_1_, p_doRender_9_);
            boolean shouldSit = p_doRender_1_.isRiding() && p_doRender_1_.ridingEntity != null && p_doRender_1_.ridingEntity.shouldRiderSit();
            this.mainModel.isRiding = shouldSit;
            this.mainModel.isChild = p_doRender_1_.isChild();

            try {
                float f = this.interpolateRotation(p_doRender_1_.prevRenderYawOffset, p_doRender_1_.renderYawOffset, p_doRender_9_);
                float f1 = this.interpolateRotation(p_doRender_1_.prevRotationYawHead, p_doRender_1_.rotationYawHead, p_doRender_9_);
                float f2 = f1 - f;
                float f8;
                if (shouldSit && p_doRender_1_.ridingEntity instanceof EntityLivingBase) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)p_doRender_1_.ridingEntity;
                    f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, p_doRender_9_);
                    f2 = f1 - f;
                    f8 = MathHelper.wrapAngleTo180_float(f2);
                    if (f8 < -85.0F) {
                        f8 = -85.0F;
                    }

                    if (f8 >= 85.0F) {
                        f8 = 85.0F;
                    }

                    f = f1 - f8;
                    if (f8 * f8 > 2500.0F) {
                        f += f8 * 0.2F;
                    }
                }

                float f7 = p_doRender_1_ instanceof EntityPlayerSP
                        ? RotationPitchHead.prevRotationPitchHead + (RotationPitchHead.rotationPitchHead - RotationPitchHead.prevRotationPitchHead) * p_doRender_9_
                        :p_doRender_1_.prevRotationPitch + (p_doRender_1_.rotationPitch - p_doRender_1_.prevRotationPitch) * p_doRender_9_;
                this.renderLivingAt(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_6_);
                f8 = this.handleRotationFloat(p_doRender_1_, p_doRender_9_);
                this.rotateCorpse(p_doRender_1_, f8, f, p_doRender_9_);
                GlStateManager.enableRescaleNormal();
                GlStateManager.scale(-1.0F, -1.0F, 1.0F);
                this.preRenderCallback(p_doRender_1_, p_doRender_9_);
                float f4 = 0.0625F;
                GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
                float f5 = p_doRender_1_.prevLimbSwingAmount + (p_doRender_1_.limbSwingAmount - p_doRender_1_.prevLimbSwingAmount) * p_doRender_9_;
                float f6 = p_doRender_1_.limbSwing - p_doRender_1_.limbSwingAmount * (1.0F - p_doRender_9_);
                if (p_doRender_1_.isChild()) {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F) {
                    f5 = 1.0F;
                }

                GlStateManager.enableAlpha();
                this.mainModel.setLivingAnimations(p_doRender_1_, f6, f5, p_doRender_9_);
                this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, 0.0625F, p_doRender_1_);
                boolean flag;
                if (this.renderOutlines) {
                    flag = this.setScoreTeamColor(p_doRender_1_);
                    this.renderModel(p_doRender_1_, f6, f5, f8, f2, f7, 0.0625F);
                    if (flag) {
                        this.unsetScoreTeamColor();
                    }
                } else {
                    flag = this.setDoRenderBrightness(p_doRender_1_, p_doRender_9_);
                    this.renderModel(p_doRender_1_, f6, f5, f8, f2, f7, 0.0625F);
                    if (flag) {
                        this.unsetBrightness();
                    }

                    GlStateManager.depthMask(true);
                    if (!(p_doRender_1_ instanceof EntityPlayer) || !((EntityPlayer)p_doRender_1_).isSpectator()) {
                        this.renderLayers(p_doRender_1_, f6, f5, p_doRender_9_, f8, f2, f7, 0.0625F);
                    }
                }

                GlStateManager.disableRescaleNormal();
            } catch (Exception var20) {
                System.out.println("Couldn't render entity");
//                logger.error("Couldn't render entity", var20);
            }

            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (!this.renderOutlines) {
                super.doRender(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_6_, p_doRender_8_, p_doRender_9_);
            }

            MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(p_doRender_1_, (RendererLivingEntity) (Object)this, p_doRender_2_, p_doRender_4_, p_doRender_6_));
        }

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
