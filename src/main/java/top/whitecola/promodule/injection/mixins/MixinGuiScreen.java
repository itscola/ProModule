package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import top.whitecola.promodule.utils.BlurUtils;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {


    /**
     * @author White_cola
     * @reason blur
     */
    @Overwrite
    public void drawDefaultBackground() {
        BlurUtils.doBlur(7);
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
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
