package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.modules.impls.other.GUIBlur;
import top.whitecola.promodule.utils.BlurUtils;
import static top.whitecola.promodule.utils.MCWrapper.*;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen extends Gui {


    @Shadow public abstract void drawBackground(int p_drawBackground_1_);

    @Shadow public int width;

    @Shadow public int height;

    /**
     * @author White_cola
     * @reason blur
     */
    @Overwrite
    public void drawWorldBackground(int p_drawWorldBackground_1_) {
        if (mc.theWorld != null) {
            GUIBlur guiBlur = (GUIBlur) ProModule.getProModule().getModuleManager().getModuleByName("GUIBlur");
            if(guiBlur!=null&& guiBlur.isEnabled()){
                BlurUtils.doBlur(7);
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
            }else {
                this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
            }
        } else {
            this.drawBackground(p_drawWorldBackground_1_);
        }

    }


//    /**
//     * @author White_cola
//     * @reason blur
//     */
//    @Overwrite
//    public void drawBackground(int p_drawBackground_1_) {
//        BlurUtils.doBlur(7);
//        GlStateManager.enableBlend();
//        GlStateManager.enableDepth();
//    }



}
