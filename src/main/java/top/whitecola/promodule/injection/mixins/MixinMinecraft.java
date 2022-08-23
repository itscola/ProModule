package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.modules.impls.combat.NoClickDelay;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMixinMinecraft {
    @Shadow private int rightClickDelayTimer;

    @Shadow private Timer timer;

    @Shadow private int leftClickCounter;

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public void setRightClickDelayTimer(int value) {
        this.rightClickDelayTimer = value;
    }

    @Override
    public int getRightClickDelayTimer() {
        return this.rightClickDelayTimer;
    }

    @Inject(method = "clickMouse", at = { @At("HEAD") }, cancellable = true)
    private void clickMouse(CallbackInfo ci){
        NoClickDelay noClickDelay = (NoClickDelay) ProModule.getProModule().getModuleManager().getModuleByName("NoClickDelay");
        if(noClickDelay!=null&noClickDelay.isEnabled()){
            this.leftClickCounter = 0;
        }
    }


}
