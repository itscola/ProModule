package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMixinMinecraft {

    @Shadow private Timer timer;

    @Override
    public Timer getTimer() {
        return timer;
    }
}
