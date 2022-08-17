package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.utils.ClientPhysic;

import java.util.Random;

@Mixin(RenderEntityItem.class)
public abstract class MixinRenderEntityItem extends Render<EntityItem> {
    @Shadow
    private Random field_177079_e;

    @Shadow @Final
    private RenderItem itemRenderer;

    @Shadow protected abstract int func_177077_a(EntityItem p_177077_1_, double p_177077_2_, double p_177077_4_, double p_177077_4_2, float p_177077_6_, IBakedModel p_177077_6_2);

    @Shadow public abstract boolean shouldSpreadItems();

    protected MixinRenderEntityItem(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    /**
     * @author White_cola
     * @reason For ItemPhysic Module
     */
    @Overwrite
    public void doRender(EntityItem p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_6_, float p_doRender_8_, float p_doRender_9_) {

        ItemStack itemstack = p_doRender_1_.getEntityItem();
        this.field_177079_e.setSeed(187L);
        boolean flag = false;
        if (this.bindEntityTexture(p_doRender_1_)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(p_doRender_1_)).setBlurMipmap(false, false);
            flag = true;
        }


        if(ProModule.getProModule().getModuleManager().getModuleByName("ItemPhysic").isEnabled()){
            ClientPhysic.doRender(p_doRender_1_,p_doRender_2_,p_doRender_4_,p_doRender_6_);
            return;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
        int i = this.func_177077_a(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_6_, p_doRender_9_, ibakedmodel);

        for(int j = 0; j < i; ++j) {
            GlStateManager.pushMatrix();
            if (j > 0) {
                float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                GlStateManager.translate(this.shouldSpreadItems() ? f : 0.0F, this.shouldSpreadItems() ? f1 : 0.0F, f2);
            }

            if (ibakedmodel.isGui3d()) {
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND);
            this.itemRenderer.renderItem(itemstack, ibakedmodel);
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(p_doRender_1_);
        if (flag) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(p_doRender_1_)).restoreLastBlurMipmap();
        }

        super.doRender(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_6_, p_doRender_8_, p_doRender_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityItem p_getEntityTexture_1_) {
        return TextureMap.locationBlocksTexture;
    }
}
