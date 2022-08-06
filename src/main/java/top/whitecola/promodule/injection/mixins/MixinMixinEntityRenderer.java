package top.whitecola.promodule.injection.mixins;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.injection.wrappers.CanBeCollidedWith;
import top.whitecola.promodule.modules.impls.combat.Reach;
import top.whitecola.promodule.utils.RandomUtils;

import java.util.List;
import java.util.Random;

@Mixin(EntityRenderer.class)
public abstract class MixinMixinEntityRenderer {

    @Shadow
    private Minecraft mc;

    @Shadow
    private Entity pointedEntity;

    @Shadow private Random random;

    @Shadow protected abstract boolean isDrawBlockOutline();

    @Shadow protected abstract void updateFogColor(float p_updateFogColor_1_);

    @Shadow protected abstract void setupCameraTransform(float p_setupCameraTransform_1_, int p_setupCameraTransform_2_);

    @Shadow protected abstract void setupFog(int p_setupFog_1_, float p_setupFog_2_);

    @Shadow protected abstract float getFOVModifier(float p_getFOVModifier_1_, boolean p_getFOVModifier_2_);

    @Shadow protected abstract void renderCloudsCheck(RenderGlobal p_renderCloudsCheck_1_, float p_renderCloudsCheck_2_, int p_renderCloudsCheck_3_);

    @Shadow private boolean debugView;

    @Shadow public abstract void disableLightmap();

    @Shadow private int frameCount;

    @Shadow private float farPlaneDistance;

    @Shadow public abstract void enableLightmap();

    @Shadow protected abstract void renderRainSnow(float p_renderRainSnow_1_);

    @Shadow private boolean renderHand;

    @Shadow protected abstract void renderWorldDirections(float p_renderWorldDirections_1_);

    @Shadow protected abstract void renderHand(float p_renderHand_1_, int p_renderHand_2_);

    /**
     * @author white_cola
     * @reason reach module.
     */
    @Overwrite
    public void getMouseOver(float p_getMouseOver_1_) {
        Entity entity = this.mc.getRenderViewEntity();
        if (entity != null && this.mc.theWorld != null) {

            double reach = 3.0;

            if(ProModule.getProModule().getModuleManager().getModuleByName("Reach").isEnabled()){
                double minRange = ((Reach)ProModule.getProModule().getModuleManager().getModuleByName("Reach")).minRange;
                double maxRange = ((Reach)ProModule.getProModule().getModuleManager().getModuleByName("Reach")).maxRange;
                reach = RandomUtils.nextDouble(minRange,maxRange);
            }

            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double d0 = (double)this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity.rayTrace(d0, p_getMouseOver_1_);
            double d1 = d0;
            Vec3 vec3 = entity.getPositionEyes(p_getMouseOver_1_);
            boolean flag = false;
            if (this.mc.playerController.extendedReach()) {
                d0 = 6.0D;
                d1 = 6.0D;
            } else if (d0 > reach) {
                flag = true;
            }

            if (this.mc.objectMouseOver != null) {
                d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(p_getMouseOver_1_);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            this.pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, new CanBeCollidedWith()));
            double d2 = d1;

            for(int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity)list.get(j);
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        this.pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1 == entity.ridingEntity && !entity.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                this.pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                            }
                        } else {
                            this.pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > reach) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing)null, new BlockPos(vec33));
            }

            if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }

            this.mc.mcProfiler.endSection();
        }


    }


    /**
     * @author White_cola
     * @reason For Render Event.
     */
    @Overwrite
    private void renderWorldPass(int p_renderWorldPass_1_, float p_renderWorldPass_2_, long p_renderWorldPass_3_) {
        RenderGlobal renderglobal = this.mc.renderGlobal;
        EffectRenderer effectrenderer = this.mc.effectRenderer;
        boolean flag = this.isDrawBlockOutline();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("clear");
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        this.updateFogColor(p_renderWorldPass_2_);
        GlStateManager.clear(16640);
        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(p_renderWorldPass_2_, p_renderWorldPass_1_);
        ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        ICamera icamera = new Frustum();
        Entity entity = this.mc.getRenderViewEntity();
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)p_renderWorldPass_2_;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)p_renderWorldPass_2_;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)p_renderWorldPass_2_;
        icamera.setPosition(d0, d1, d2);
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            this.setupFog(-1, p_renderWorldPass_2_);
            this.mc.mcProfiler.endStartSection("sky");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(p_renderWorldPass_2_, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
            GlStateManager.matrixMode(5888);
            renderglobal.renderSky(p_renderWorldPass_2_, p_renderWorldPass_1_);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(this.getFOVModifier(p_renderWorldPass_2_, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * MathHelper.SQRT_2);
            GlStateManager.matrixMode(5888);
        }

        this.setupFog(0, p_renderWorldPass_2_);
        GlStateManager.shadeModel(7425);
        if (entity.posY + (double)entity.getEyeHeight() < 128.0D) {
            this.renderCloudsCheck(renderglobal, p_renderWorldPass_2_, p_renderWorldPass_1_);
        }

        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, p_renderWorldPass_2_);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection("terrain_setup");
        renderglobal.setupTerrain(entity, (double)p_renderWorldPass_2_, icamera, this.frameCount++, this.mc.thePlayer.isSpectator());
        if (p_renderWorldPass_1_ == 0 || p_renderWorldPass_1_ == 2) {
            this.mc.mcProfiler.endStartSection("updatechunks");
            this.mc.renderGlobal.updateChunks(p_renderWorldPass_3_);
        }

        this.mc.mcProfiler.endStartSection("terrain");
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, (double)p_renderWorldPass_2_, p_renderWorldPass_1_, entity);
        GlStateManager.enableAlpha();
        renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, (double)p_renderWorldPass_2_, p_renderWorldPass_1_, entity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
        renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, (double)p_renderWorldPass_2_, p_renderWorldPass_1_, entity);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        GlStateManager.shadeModel(7424);
        GlStateManager.alphaFunc(516, 0.1F);
        EntityPlayer entityplayer1;
        if (!this.debugView) {
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            ForgeHooksClient.setRenderPass(0);
            renderglobal.renderEntities(entity, icamera, p_renderWorldPass_2_);
            ForgeHooksClient.setRenderPass(0);
            RenderHelper.disableStandardItemLighting();
            this.disableLightmap();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag) {
                entityplayer1 = (EntityPlayer)entity;
                GlStateManager.disableAlpha();
                this.mc.mcProfiler.endStartSection("outline");
                if (!ForgeHooksClient.onDrawBlockHighlight(renderglobal, entityplayer1, this.mc.objectMouseOver, 0, entityplayer1.getHeldItem(), p_renderWorldPass_2_)) {
                    renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, p_renderWorldPass_2_);
                }

                GlStateManager.enableAlpha();
            }
        }

        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        if (flag && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
            entityplayer1 = (EntityPlayer)entity;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection("outline");
            if (!ForgeHooksClient.onDrawBlockHighlight(renderglobal, entityplayer1, this.mc.objectMouseOver, 0, entityplayer1.getHeldItem(), p_renderWorldPass_2_)) {
                renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, p_renderWorldPass_2_);
            }

            GlStateManager.enableAlpha();
        }

        this.mc.mcProfiler.endStartSection("destroyProgress");
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
        renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, p_renderWorldPass_2_);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
        GlStateManager.disableBlend();
        if (!this.debugView) {
            this.enableLightmap();
            this.mc.mcProfiler.endStartSection("litParticles");
            effectrenderer.renderLitParticles(entity, p_renderWorldPass_2_);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, p_renderWorldPass_2_);
            this.mc.mcProfiler.endStartSection("particles");
            effectrenderer.renderParticles(entity, p_renderWorldPass_2_);
            this.disableLightmap();
        }

        GlStateManager.depthMask(false);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("weather");
        this.renderRainSnow(p_renderWorldPass_2_);
        GlStateManager.depthMask(true);
        renderglobal.renderWorldBorder(entity, p_renderWorldPass_2_);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1F);
        this.setupFog(0, p_renderWorldPass_2_);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(7425);
        this.mc.mcProfiler.endStartSection("translucent");
        renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, (double)p_renderWorldPass_2_, p_renderWorldPass_1_, entity);
        if (!this.debugView) {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            ForgeHooksClient.setRenderPass(1);
            renderglobal.renderEntities(entity, icamera, p_renderWorldPass_2_);
            ForgeHooksClient.setRenderPass(-1);
            RenderHelper.disableStandardItemLighting();
        }

        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        if (entity.posY + (double)entity.getEyeHeight() >= 128.0D) {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.renderCloudsCheck(renderglobal, p_renderWorldPass_2_, p_renderWorldPass_1_);
        }

        this.mc.mcProfiler.endStartSection("forge_render_last");
        ForgeHooksClient.dispatchRenderLast(renderglobal, p_renderWorldPass_2_);
        this.mc.mcProfiler.endStartSection("hand");


        WorldRenderEvent worldRenderEvent = new WorldRenderEvent(p_renderWorldPass_2_);
        EventManager.getEventManager().worldRenderEvent(worldRenderEvent);

        if (!ForgeHooksClient.renderFirstPersonHand(renderglobal, p_renderWorldPass_2_, p_renderWorldPass_1_) && this.renderHand) {
            GlStateManager.clear(256);
            this.renderHand(p_renderWorldPass_2_, p_renderWorldPass_1_);
            this.renderWorldDirections(p_renderWorldPass_2_);
        }

    }
}
