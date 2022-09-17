package top.whitecola.promodule.injection.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.fonts.font2.FontRenderer;

import java.awt.*;

@Mixin(net.minecraft.client.gui.Gui.class)
public class MixinGUI {
    public Color color = new Color(59, 120, 220);

    @Inject(method = "drawCenteredString", at = @At(value = "HEAD"), cancellable = true)
    public void drawCenteredString(net.minecraft.client.gui.FontRenderer p_drawCenteredString_1_, String p_drawCenteredString_2_, int p_drawCenteredString_3_, int p_drawCenteredString_4_, int p_drawCenteredString_5_, CallbackInfo ci){
        if(p_drawCenteredString_2_.equals("ProModule")){
            p_drawCenteredString_1_.drawStringWithShadow(p_drawCenteredString_2_, (float)(p_drawCenteredString_3_ - p_drawCenteredString_1_.getStringWidth(p_drawCenteredString_2_) / 2), (float)p_drawCenteredString_4_, color.getRGB());

            ci.cancel();
            return;

        }

    }

}