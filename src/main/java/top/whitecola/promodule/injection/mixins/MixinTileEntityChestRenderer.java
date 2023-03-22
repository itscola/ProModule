package top.whitecola.promodule.injection.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.events.EventManager;
import top.whitecola.promodule.events.impls.event.RenderChestEvent;

@Mixin(TileEntityChestRenderer.class)
public class MixinTileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {


    @Shadow private ModelChest simpleChest;

    @Shadow private boolean isChristams;

    @Shadow @Final private static ResourceLocation textureChristmas;

    @Shadow @Final private static ResourceLocation textureTrapped;

    @Shadow @Final private static ResourceLocation textureNormal;

    @Shadow private ModelChest largeChest;

    @Shadow @Final private static ResourceLocation textureChristmasDouble;

    @Shadow @Final private static ResourceLocation textureTrappedDouble;

    @Shadow @Final private static ResourceLocation textureNormalDouble;

    /**
     * @author White_cola
     * @reason chestRenderEvent
     */
    @Overwrite
    public void renderTileEntityAt(TileEntityChest p_renderTileEntityAt_1_, double p_renderTileEntityAt_2_, double p_renderTileEntityAt_4_, double p_renderTileEntityAt_6_, float p_renderTileEntityAt_8_, int p_renderTileEntityAt_9_) {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        int lvt_10_2_;
        if (!p_renderTileEntityAt_1_.hasWorldObj()) {
            lvt_10_2_ = 0;
        } else {
            Block lvt_11_1_ = p_renderTileEntityAt_1_.getBlockType();
            lvt_10_2_ = p_renderTileEntityAt_1_.getBlockMetadata();
            if (lvt_11_1_ instanceof BlockChest && lvt_10_2_ == 0) {
                ((BlockChest)lvt_11_1_).checkForSurroundingChests(p_renderTileEntityAt_1_.getWorld(), p_renderTileEntityAt_1_.getPos(), p_renderTileEntityAt_1_.getWorld().getBlockState(p_renderTileEntityAt_1_.getPos()));
                lvt_10_2_ = p_renderTileEntityAt_1_.getBlockMetadata();
            }

            p_renderTileEntityAt_1_.checkForAdjacentChests();
        }

        if (p_renderTileEntityAt_1_.adjacentChestZNeg == null && p_renderTileEntityAt_1_.adjacentChestXNeg == null) {
            ModelChest lvt_11_3_;
            if (p_renderTileEntityAt_1_.adjacentChestXPos == null && p_renderTileEntityAt_1_.adjacentChestZPos == null) {
                lvt_11_3_ = this.simpleChest;
                if (p_renderTileEntityAt_9_ >= 0) {
                    this.bindTexture(DESTROY_STAGES[p_renderTileEntityAt_9_]);

                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(4.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                } else if (this.isChristams) {
                    this.bindTexture(textureChristmas);
                } else if (p_renderTileEntityAt_1_.getChestType() == 1) {
                    this.bindTexture(textureTrapped);
                } else {
                    this.bindTexture(textureNormal);
                }
            } else {
                lvt_11_3_ = this.largeChest;
                if (p_renderTileEntityAt_9_ >= 0) {
                    this.bindTexture(DESTROY_STAGES[p_renderTileEntityAt_9_]);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(8.0F, 4.0F, 1.0F);
                    GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                    GlStateManager.matrixMode(5888);
                } else if (this.isChristams) {
                    this.bindTexture(textureChristmasDouble);
                } else if (p_renderTileEntityAt_1_.getChestType() == 1) {
                    this.bindTexture(textureTrappedDouble);
                } else {
                    this.bindTexture(textureNormalDouble);
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            if (p_renderTileEntityAt_9_ < 0) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.translate((float)p_renderTileEntityAt_2_, (float)p_renderTileEntityAt_4_ + 1.0F, (float)p_renderTileEntityAt_6_ + 1.0F);
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            GlStateManager.translate(0.5F, 0.5F, 0.5F);
            int lvt_12_1_ = 0;
            if (lvt_10_2_ == 2) {
                lvt_12_1_ = 180;
            }

            if (lvt_10_2_ == 3) {
                lvt_12_1_ = 0;
            }

            if (lvt_10_2_ == 4) {
                lvt_12_1_ = 90;
            }

            if (lvt_10_2_ == 5) {
                lvt_12_1_ = -90;
            }

            if (lvt_10_2_ == 2 && p_renderTileEntityAt_1_.adjacentChestXPos != null) {
                GlStateManager.translate(1.0F, 0.0F, 0.0F);
            }

            if (lvt_10_2_ == 5 && p_renderTileEntityAt_1_.adjacentChestZPos != null) {
                GlStateManager.translate(0.0F, 0.0F, -1.0F);
            }

            GlStateManager.rotate((float)lvt_12_1_, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            float lvt_13_1_ = p_renderTileEntityAt_1_.prevLidAngle + (p_renderTileEntityAt_1_.lidAngle - p_renderTileEntityAt_1_.prevLidAngle) * p_renderTileEntityAt_8_;
            float lvt_14_2_;
            if (p_renderTileEntityAt_1_.adjacentChestZNeg != null) {
                lvt_14_2_ = p_renderTileEntityAt_1_.adjacentChestZNeg.prevLidAngle + (p_renderTileEntityAt_1_.adjacentChestZNeg.lidAngle - p_renderTileEntityAt_1_.adjacentChestZNeg.prevLidAngle) * p_renderTileEntityAt_8_;
                if (lvt_14_2_ > lvt_13_1_) {
                    lvt_13_1_ = lvt_14_2_;
                }
            }

            if (p_renderTileEntityAt_1_.adjacentChestXNeg != null) {
                lvt_14_2_ = p_renderTileEntityAt_1_.adjacentChestXNeg.prevLidAngle + (p_renderTileEntityAt_1_.adjacentChestXNeg.lidAngle - p_renderTileEntityAt_1_.adjacentChestXNeg.prevLidAngle) * p_renderTileEntityAt_8_;
                if (lvt_14_2_ > lvt_13_1_) {
                    lvt_13_1_ = lvt_14_2_;
                }
            }

            lvt_13_1_ = 1.0F - lvt_13_1_;
            lvt_13_1_ = 1.0F - lvt_13_1_ * lvt_13_1_ * lvt_13_1_;
            lvt_11_3_.chestLid.rotateAngleX = -(lvt_13_1_ * 3.1415927F / 2.0F);



                RenderChestEvent renderChestEvent = new RenderChestEvent(p_renderTileEntityAt_1_, lvt_11_3_::renderAll);
                EventManager.getEventManager().onRenderChest(renderChestEvent);

                if(!renderChestEvent.isCancelled()) {
                    renderChestEvent.drawChest();
                }






            lvt_11_3_.renderAll();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (p_renderTileEntityAt_9_ >= 0) {
                GlStateManager.matrixMode(5890);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
            }

        }
    }
}
