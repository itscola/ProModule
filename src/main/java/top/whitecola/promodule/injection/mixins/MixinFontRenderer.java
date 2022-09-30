package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.fonts.font2.FontLoaders;
import top.whitecola.promodule.modules.impls.other.BetterFont;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "renderString", at = { @At("HEAD") }, cancellable = true)
    public void renderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> cir){
        BetterFont betterFont = (BetterFont) ProModule.getProModule().getModuleManager().getModuleByName("BetterFont");
        if(betterFont!=null&betterFont.isEnabled()){
            FontLoaders.msFont14.renderString(text,x,y,color,dropShadow);
            cir.cancel();
            return;
        }
    }


//    @Inject(method = "drawString(Ljava/lang/String;III)I", at = { @At("HEAD") }, cancellable = true)
//    public void drawString(String text, int x, int y, int color, CallbackInfoReturnable<Integer> cir){
//        BetterFont betterFont = (BetterFont) ProModule.getProModule().getModuleManager().getModuleByName("BetterFont");
//        if(betterFont!=null&betterFont.isEnabled()){
//            FontLoaders.getMsFont14().drawString(text,x,y,color);
//            cir.cancel();
//            return;
//        }
//    }
//
//    @Inject(method = "drawStringWithShadow", at = { @At("HEAD") }, cancellable = true)
//    public void drawStringWithShadow(String text, float x, float y, int color, CallbackInfoReturnable<Integer> cir){
//        BetterFont betterFont = (BetterFont) ProModule.getProModule().getModuleManager().getModuleByName("BetterFont");
//        if(betterFont!=null&betterFont.isEnabled()){
//            FontLoaders.getMsFont14().drawStringWithShadow(text,x,y,color);
//            cir.cancel();
//            return;
//        }
//    }
}
