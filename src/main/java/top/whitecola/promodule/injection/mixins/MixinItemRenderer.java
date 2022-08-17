package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.ProModule;

import static top.whitecola.promodule.utils.MCWrapper.*;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @Shadow
    private float prevEquippedProgress;


    @Shadow private float equippedProgress;

    @Shadow protected abstract void func_178101_a(float p_178101_1_, float p_178101_2_);

    @Shadow protected abstract void func_178109_a(AbstractClientPlayer p_178109_1_);

    @Shadow protected abstract void func_178110_a(EntityPlayerSP p_178110_1_, float p_178110_2_);

    @Shadow private ItemStack itemToRender;

    @Shadow protected abstract void renderItemMap(AbstractClientPlayer p_renderItemMap_1_, float p_renderItemMap_2_, float p_renderItemMap_3_, float p_renderItemMap_4_);

    @Shadow protected abstract void transformFirstPersonItem(float p_transformFirstPersonItem_1_, float p_transformFirstPersonItem_2_);

    @Shadow protected abstract void func_178104_a(AbstractClientPlayer p_178104_1_, float p_178104_2_);

    @Shadow protected abstract void func_178103_d();

    @Shadow protected abstract void func_178098_a(float p_178098_1_, AbstractClientPlayer p_178098_2_);

    @Shadow protected abstract void func_178105_d(float p_178105_1_);

    @Shadow public abstract void renderItem(EntityLivingBase p_renderItem_1_, ItemStack p_renderItem_2_, ItemCameraTransforms.TransformType p_renderItem_3_);

    @Shadow protected abstract void func_178095_a(AbstractClientPlayer p_178095_1_, float p_178095_2_, float p_178095_3_);

    /**
     * @author White_cola
     * @reason For old animations
     */

    @Overwrite
    public void renderItemInFirstPerson(float p_renderItemInFirstPerson_1_) {




        float f = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * p_renderItemInFirstPerson_1_);
        AbstractClientPlayer abstractclientplayer = mc.thePlayer;
        float f1 = abstractclientplayer.getSwingProgress(p_renderItemInFirstPerson_1_);
        float f2 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * p_renderItemInFirstPerson_1_;
        float f3 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * p_renderItemInFirstPerson_1_;


        this.func_178101_a(f2, f3);
        this.func_178109_a(abstractclientplayer);
        this.func_178110_a((EntityPlayerSP)abstractclientplayer, p_renderItemInFirstPerson_1_);



        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            if (this.itemToRender.getItem() instanceof ItemMap) {
                this.renderItemMap(abstractclientplayer, f2, f, f1);
            } else if (abstractclientplayer.getItemInUseCount() > 0) {
                EnumAction enumaction = this.itemToRender.getItemUseAction();
                switch(enumaction) {
                    case NONE:
                        this.transformFirstPersonItem(f, 0.0F);
                        break;
                    case EAT:
                    case DRINK:
                        this.func_178104_a(abstractclientplayer, p_renderItemInFirstPerson_1_);
                        if(ProModule.getProModule().getModuleManager().getModuleByName("OldAnimation").isEnabled()) {
                            this.transformFirstPersonItem(f, f1);
                        }else {
                            this.transformFirstPersonItem(f, 0.0F);

                        }
                        break;

                    case BLOCK:
                        if(ProModule.getProModule().getModuleManager().getModuleByName("OldAnimation").isEnabled()){
                            this.transformFirstPersonItem(f, f1);
                            GlStateManager.translate(0.0F, 0.0F, -0.02F);
                            GlStateManager.rotate(1.0F, 0.0F, 0.0F, -0.1F);
                        }else{
                            this.transformFirstPersonItem(f, 0.0F);
                        }
                        this.func_178103_d();
                        break;
                    case BOW:
                        if(ProModule.getProModule().getModuleManager().getModuleByName("OldAnimation").isEnabled()) {
                            this.transformFirstPersonItem(f, f1);
                            GlStateManager.translate(0.0F, 0.0F, -0.02F);
                            GlStateManager.rotate(1.0F, 0.0F, 0.0F, -0.1F);
                        }else {
                            this.transformFirstPersonItem(f, 0.0F);
                        }
                        this.func_178098_a(p_renderItemInFirstPerson_1_, abstractclientplayer);
                }
            } else {
                this.func_178105_d(f1);
                this.transformFirstPersonItem(f, f1);
            }

            this.renderItem(abstractclientplayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!abstractclientplayer.isInvisible()) {
            this.func_178095_a(abstractclientplayer, f, f1);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
}
